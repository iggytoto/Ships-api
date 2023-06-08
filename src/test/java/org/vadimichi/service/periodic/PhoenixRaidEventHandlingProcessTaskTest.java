package org.vadimichi.service.periodic;

import org.vadimichi.controller.UseCaseTestBase;
import org.gassangaming.model.event.Event;
import org.gassangaming.model.event.EventInstanceStatus;
import org.gassangaming.model.event.EventStatus;
import org.gassangaming.model.event.EventType;
import org.gassangaming.model.unit.human.HumanWarrior;
import org.gassangaming.repository.event.EventInstanceRepository;
import org.gassangaming.repository.event.EventRepository;
import org.gassangaming.service.event.EventsService;
import org.gassangaming.service.exception.ServiceException;
import org.gassangaming.service.periodic.events.PhoenixRaidEventHandlingProcessTask;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PhoenixRaidEventHandlingProcessTaskTest extends UseCaseTestBase {
    @Autowired
    PhoenixRaidEventHandlingProcessTask task;
    @Autowired
    EventsService eventsService;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventInstanceRepository eventInstanceRepository;


    @Before
    public void setup() throws ServiceException {
        final var userRosters = new HashMap<Long, List<Long>>();
        final var userIds = new ArrayList<Long>();
        for (int i = 1; i < 7; i++) {
            userIds.add(registerUser(String.valueOf(i), String.valueOf(i)));
        }
        for (var userId : userIds) {
            final var unitIds = new ArrayList<Long>();
            for (int i = 1; i < 7; i++) {
                unitIds.add(addUnit(new HumanWarrior(), userId).getId());
            }
            userRosters.put(userId, unitIds);
        }
        eventRepository.save(new Event(EventType.PhoenixRaid, EventStatus.Planned));
        for (var entry : userRosters.entrySet()) {
            eventsService.register(entry.getValue(), EventType.PhoenixRaid, entry.getKey());
        }
    }

    @Test
    public void test() {
        final var oldEvent = eventRepository.findLatestPlannedByType(EventType.PhoenixRaid);
        task.scheduledPhoenixEventHandler();
        final var instances = eventInstanceRepository.findAll().stream().filter(ei -> ei.getEventType() == EventType.PhoenixRaid).toList();
        Assert.assertEquals(2, instances.size());
        Assert.assertTrue(instances.stream().allMatch(i -> i.getStatus().equals(EventInstanceStatus.WaitingForServer)));
        final var newEvent = eventRepository.findLatestPlannedByType(EventType.PhoenixRaid);
        Assert.assertNotNull(newEvent);
        Assert.assertNotEquals(newEvent.getId(), oldEvent.getId());
        Assert.assertEquals(EventStatus.Planned, newEvent.getStatus());
        final var updatedOldEvent = eventRepository.findById(oldEvent.getId()).orElseThrow();
        Assert.assertEquals(EventStatus.InProgress, updatedOldEvent.getStatus());
    }
}
