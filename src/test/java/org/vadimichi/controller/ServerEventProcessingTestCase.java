package org.vadimichi.controller;

import org.vadimichi.dto.controllers.events.EventInstanceDataRequestDto;
import org.vadimichi.dto.controllers.events.EventInstanceDto;
import org.vadimichi.dto.controllers.events.EventInstanceDataDto;
import org.vadimichi.dto.controllers.events.ServerApplicationRequestDto;
import org.vadimichi.dto.controllers.events.eventinstanceresult.EventInstanceResultDto;
import org.gassangaming.model.event.*;
import org.gassangaming.model.unit.Activity;
import org.gassangaming.model.unit.human.HumanWarrior;
import org.gassangaming.repository.event.UnitEventRegistrationRepository;
import org.gassangaming.repository.event.UserEventInstanceRepository;
import org.gassangaming.repository.event.UserEventRegistrationRepository;
import org.gassangaming.service.event.result.EventInstanceResult;
import org.gassangaming.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * - server applies to process
 * - server gets event data
 * - server saves event result
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerEventProcessingTestCase extends UseCaseTestBase {

    private static final String LOGIN = "oaubhreg";
    private static final String PASSWORD = "oaubhreg";

    private static final String HOST = "aebaerba";
    private static final String PORT = "ageger";

    @Autowired
    EventsController eventsController;
    @Autowired
    UserEventRegistrationRepository userEventRegistrationRepository;
    @Autowired
    UnitEventRegistrationRepository unitEventRegistrationRepository;
    @Autowired
    UserEventInstanceRepository userEventInstanceRepository;
    private long eventInstanceId;
    private long eventId;
    private long unitSurvivedId;
    private long unitDeadId;

    @Before
    public void setup() throws ServiceException {
        userId = registerServerUser(LOGIN, PASSWORD);
        context = login(LOGIN, PASSWORD);
        eventId = addEvent(new Event(EventType.Test, EventStatus.InProgress)).getId();
        eventInstanceId = addEventInstance(new EventInstance(eventId, EventType.Test, EventInstanceStatus.WaitingForServer)).getId();
        unitSurvivedId = addUnit(HumanWarrior.of(Activity.Event), userId).getId();
        unitDeadId = addUnit(HumanWarrior.of(Activity.Event), userId).getId();
        userEventInstanceRepository.save(new UserEventInstance(userId, eventId, eventInstanceId));
        userEventRegistrationRepository.save(new UserEventRegistration(userId, eventId));
        unitEventRegistrationRepository.save(new UnitEventRegistration(eventId, unitSurvivedId, userId));
        unitEventRegistrationRepository.save(new UnitEventRegistration(eventId, unitDeadId, userId));

    }

    @Test
    public void testCase() {
        //apply as server
        final var applyAsServerRequest = new ServerApplicationRequestDto();
        applyAsServerRequest.setHost(HOST);
        applyAsServerRequest.setPort(PORT);
        final var response = (EventInstanceDto) eventsController.apply(context, applyAsServerRequest);
        Assert.assertNotNull(response);
        Assert.assertEquals(eventInstanceId, response.getId());
        Assert.assertEquals(eventId, response.getEventId());
        Assert.assertEquals(HOST, response.getHost());
        Assert.assertEquals(PORT, response.getPort());
        Assert.assertEquals(EventInstanceStatus.WaitingForPlayers, response.getStatus());
        Assert.assertEquals(EventType.Test, response.getEventType());
        // get event data
        final var eventInstanceDataRequestDto = new EventInstanceDataRequestDto();
        eventInstanceDataRequestDto.setEventInstanceId(eventInstanceId);
        final var dataResponse = (EventInstanceDataDto) eventsController.getData(eventInstanceDataRequestDto);
        Assert.assertNotNull(dataResponse);
        Assert.assertEquals(2, dataResponse.getEventParticipants().size());
        //save result
        final var saveResultDto = new TestResultDto();
        saveResultDto.setEventInstanceId(eventInstanceId);
        eventsController.save(context, saveResultDto);
        final var updatedEvent = eventRepository.findById(eventId).orElseThrow();
        Assert.assertEquals(EventStatus.Closed, updatedEvent.getStatus());
        final var updatedEventInstance = eventInstanceRepository.findById(eventInstanceId);
        Assert.assertTrue(updatedEventInstance.isEmpty());
        final var survivedUnit = unitRepository.findById(unitSurvivedId);
        Assert.assertEquals(Activity.Idle, survivedUnit.getActivity());
        Assert.assertEquals(1, survivedUnit.getHitPoints());
        final var diedUnit = unitRepository.findById(unitDeadId);
        Assert.assertEquals(Activity.Dead, diedUnit.getActivity());
        Assert.assertEquals(-1, diedUnit.getHitPoints());
        final var userReg = userEventRegistrationRepository.findByUserId(userId);
        Assert.assertNull(userReg);
        final var unitRegs = unitEventRegistrationRepository.findAllByEventId(eventId);
        Assert.assertTrue(unitRegs.isEmpty());
    }

    public class TestResultDto extends EventInstanceResultDto {
        @Override
        public EventInstanceResult toDomain() {
            return new EventInstanceResult() {
                @Override
                public long getEventInstanceId() {
                    return eventInstanceId;
                }

                @Override
                public EventType getEventType() {
                    return EventType.Test;
                }

                @Override
                public Map<Long, Integer> getUnitsHitPoints() {
                    final var map = new HashMap<Long, Integer>();
                    map.put(unitSurvivedId, 1);
                    map.put(unitDeadId, -1);
                    return map;
                }
            };
        }
    }


}
