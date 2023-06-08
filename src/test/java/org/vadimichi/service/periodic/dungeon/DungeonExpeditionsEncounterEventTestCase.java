package org.vadimichi.service.periodic.dungeon;

import org.vadimichi.controller.UseCaseTestBase;
import org.gassangaming.model.dungeon.DungeonExpedition;
import org.gassangaming.model.dungeon.DungeonInstance;
import org.gassangaming.model.dungeon.DungeonRoom;
import org.gassangaming.model.dungeon.DungeonRoomEventState;
import org.gassangaming.model.dungeon.event.EncounterDungeonRoomEvent;
import org.gassangaming.model.dungeon.event.TreasureDungeonRoomEvent;
import org.gassangaming.model.event.Event;
import org.gassangaming.model.item.Item;
import org.gassangaming.model.item.ItemRarity;
import org.gassangaming.model.item.ItemType;
import org.gassangaming.model.unit.Unit;
import org.gassangaming.model.unit.human.HumanArcher;
import org.gassangaming.model.unit.human.HumanCleric;
import org.gassangaming.model.unit.human.HumanSpearman;
import org.gassangaming.repository.dungeon.*;
import org.gassangaming.repository.dungeon.event.DungeonEncounterEventRepository;
import org.gassangaming.repository.dungeon.event.DungeonTreasureEventRepository;
import org.gassangaming.repository.event.UnitEventRegistrationRepository;
import org.gassangaming.repository.event.UserEventInstanceRepository;
import org.gassangaming.service.dungeon.DungeonService;
import org.gassangaming.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * initial state:
 * - two expeditions in same room
 * - room has 100% encounter event
 * - room has 100% treasure event
 * test case:
 * - process room events to trigger events
 * expected results:
 * - encounter event is triggered for both of teams
 * - treasure event is not triggered due to encounter
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DungeonExpeditionsEncounterEventTestCase extends UseCaseTestBase {

    private static final String LOGIN_1 = "naoihegdrnoia";
    private static final String PASSWORD_1 = "naoihegdrnoia";

    private static final String LOGIN_2 = "naoihegdasdrnoia";
    private static final String PASSWORD_2 = "naoihegdrasdnoia";

    private static final String LOGIN_WORLD = "naoihegdasasdrnoia";
    private static final String PASSWORD_WORLD = "naoihegdraassdnoia";

    @Autowired
    DungeonExpeditionRoomEventProcessTask task;
    @Autowired
    DungeonInstanceRepository dungeonInstanceRepository;
    @Autowired
    DungeonRoomRepository dungeonRoomRepository;
    @Autowired
    DungeonService dungeonService;
    @Autowired
    DungeonTreasureEventRepository treasureEventRepository;
    @Autowired
    DungeonEncounterEventRepository dungeonEncounterEventRepository;
    @Autowired
    DungeonExpeditionRepository dungeonExpeditionRepository;
    @Autowired
    UnitEventRegistrationRepository unitEventRegistrationRepository;
    @Autowired
    UserEventInstanceRepository userEventInstanceRepository;

    private DungeonExpedition expedition1;
    private DungeonExpedition expedition2;
    private Event dungeonEncounterEvent;
    private final List<Unit> encounterUnits = new ArrayList<>();
    private long user1Id;
    private long user2Id;
    private TreasureDungeonRoomEvent treasureEvent;
    private EncounterDungeonRoomEvent encounterEvent;

    @Before
    public void setup() throws ServiceException {
        //dungeon encounter event
        dungeonEncounterEvent = getEncounterEvent();
        //dungeon
        final var dungeonInstance = dungeonInstanceRepository.save(new DungeonInstance());
        DungeonRoom room = dungeonRoomRepository.save(new DungeonRoom(true, dungeonInstance));
        Item treasure = addItem(new Item(ItemType.Boots, "WoodyBoot", ItemRarity.Legendary));
        treasureEvent = treasureEventRepository.save(new TreasureDungeonRoomEvent(treasure.getId(), room, "Test treasure", 1, false));
        long worldUserId = registerUser(LOGIN_WORLD, PASSWORD_WORLD);
        final var u = addUnit(new HumanSpearman(), worldUserId);
        encounterUnits.add(u);
        encounterEvent = dungeonEncounterEventRepository.save(new EncounterDungeonRoomEvent(List.of(u), 1, false, room));
        // setting up user1 and units
        final var roster1 = new ArrayList<Unit>();
        user1Id = registerUser(LOGIN_1, PASSWORD_1);
        roster1.add(addUnit(new HumanArcher(), user1Id));
        roster1.add(addUnit(new HumanCleric(), user1Id));
        roster1.add(addUnit(new HumanSpearman(), user1Id));
        encounterUnits.addAll(roster1);
        // registering
        expedition1 = dungeonService.createExpedition(roster1.stream().map(Unit::getId).toList(), room.getId(), dungeonInstance.getId(), user1Id);
        // setting up user1 and units
        final var roster2 = new ArrayList<Unit>();
        user2Id = registerUser(LOGIN_2, PASSWORD_2);
        roster2.add(addUnit(new HumanArcher(), user2Id));
        roster2.add(addUnit(new HumanCleric(), user2Id));
        roster2.add(addUnit(new HumanSpearman(), user2Id));
        encounterUnits.addAll(roster2);
        // registering
        expedition2 = dungeonService.createExpedition(roster2.stream().map(Unit::getId).toList(), room.getId(), dungeonInstance.getId(), user2Id);
    }


    @Test
    public void testCase() {
        task.scheduleRoomEvents();
        final var updatedExpedition1 = dungeonExpeditionRepository.findById(expedition1.getId()).orElseThrow();
        Assert.assertTrue(updatedExpedition1.isEncountered());
        final var updatedExpedition2 = dungeonExpeditionRepository.findById(expedition2.getId()).orElseThrow();
        Assert.assertTrue(updatedExpedition2.isEncountered());
        final var dungeonEncounterRegisteredUnits = unitEventRegistrationRepository.findAllByEventId(dungeonEncounterEvent.getId());
        Assert.assertTrue(encounterUnits.stream().allMatch(u -> dungeonEncounterRegisteredUnits.stream().anyMatch(uInner -> u.getId() == uInner.getUnitId())));
        final var eventInstances = userEventInstanceRepository.getAllByEventId(dungeonEncounterEvent.getId());
        Assert.assertEquals(2, eventInstances.size());
        Assert.assertTrue(eventInstances.stream().anyMatch(ei -> ei.getUserId() == user1Id));
        Assert.assertTrue(eventInstances.stream().anyMatch(ei -> ei.getUserId() == user2Id));
        Assert.assertEquals(eventInstances.get(0).getEventInstanceId(), eventInstances.get(1).getEventInstanceId());
        final var updatedTreasureEvent = treasureEventRepository.findById(treasureEvent.getId()).orElseThrow();
        Assert.assertEquals(DungeonRoomEventState.Active, updatedTreasureEvent.getState());
        final var updatedEncounterEvent = dungeonEncounterEventRepository.findById(encounterEvent.getId()).orElseThrow();
        Assert.assertEquals(DungeonRoomEventState.InProgress, updatedEncounterEvent.getState());
    }
}
