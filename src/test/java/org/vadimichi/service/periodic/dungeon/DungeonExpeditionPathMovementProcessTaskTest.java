package org.vadimichi.service.periodic.dungeon;

import org.vadimichi.controller.UseCaseTestBase;
import org.gassangaming.model.dungeon.DungeonExpedition;
import org.gassangaming.model.dungeon.DungeonInstance;
import org.gassangaming.model.dungeon.DungeonPath;
import org.gassangaming.model.dungeon.DungeonRoom;
import org.gassangaming.model.dungeon.event.TreasureDungeonRoomEvent;
import org.gassangaming.model.item.Item;
import org.gassangaming.model.item.ItemRarity;
import org.gassangaming.model.item.ItemType;
import org.gassangaming.model.unit.Unit;
import org.gassangaming.model.unit.human.HumanArcher;
import org.gassangaming.model.unit.human.HumanCleric;
import org.gassangaming.model.unit.human.HumanSpearman;
import org.gassangaming.repository.dungeon.*;
import org.gassangaming.repository.dungeon.event.DungeonTreasureEventRepository;
import org.gassangaming.repository.item.ItemRepository;
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


/**
 * initial state:
 * - expedition on path between two rooms
 * - at destination room there is 100% treasure event
 * test case:
 * - task moves unit to destination room
 * - treasure event processing
 * - loot added to expedition loot
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DungeonExpeditionPathMovementProcessTaskTest extends UseCaseTestBase {

    private static final String LOGIN = "naoihegrnoia";
    private static final String PASSWORD = "naoihegrnoia";

    @Autowired
    DungeonExpeditionPathMovementProcessTask task;
    @Autowired
    DungeonInstanceRepository dungeonInstanceRepository;
    @Autowired
    DungeonPathRepository dungeonPathRepository;
    @Autowired
    DungeonRoomRepository dungeonRoomRepository;
    @Autowired
    DungeonService dungeonService;
    @Autowired
    DungeonInstanceExpeditionLocationRepository dungeonInstanceExpeditionLocationRepository;
    @Autowired
    DungeonTreasureEventRepository treasureEventRepository;
    @Autowired
    DungeonExpeditionItemRepository dungeonExpeditionItemRepository;
    @Autowired
    ItemRepository itemRepository;

    private DungeonExpedition expedition;
    private DungeonRoom destinationRoom;
    private Item treasure;
    private TreasureDungeonRoomEvent treasureEvent;

    @Before
    public void setup() throws ServiceException {
        //simple dungeon
        final var dungeonInstance = dungeonInstanceRepository.save(new DungeonInstance());
        destinationRoom = dungeonRoomRepository.save(new DungeonRoom(true, dungeonInstance));
        final var startRoom = dungeonRoomRepository.save(new DungeonRoom(true, dungeonInstance));
        final var path = dungeonPathRepository.save(new DungeonPath(startRoom.getId(), destinationRoom.getId(), dungeonInstance, .001f));
        treasure = addItem(new Item(ItemType.Boots, "WoodyBoot", ItemRarity.Legendary));
        treasureEvent = treasureEventRepository.save(new TreasureDungeonRoomEvent(treasure.getId(), destinationRoom, "Test treasure", 1, false));
        // setting up user and units
        final var roster = new ArrayList<Unit>();
        userId = registerUser(LOGIN, PASSWORD);
        roster.add(addUnit(new HumanArcher(), userId));
        roster.add(addUnit(new HumanCleric(), userId));
        roster.add(addUnit(new HumanSpearman(), userId));
        // registering
        expedition = dungeonService.createExpedition(roster.stream().map(Unit::getId).toList(), startRoom.getId(), dungeonInstance.getId(), userId);
        // moving roster
        dungeonService.moveExpeditionToRoom(path.getId(), expedition.getId(), userId);
    }

    @Test
    public void testCase() {
        task.scheduleExpeditionsMovement();
        final var resultLocation = dungeonInstanceExpeditionLocationRepository.findById(expedition.getId()).orElseThrow();
        Assert.assertTrue(resultLocation.isRoom());
        Assert.assertEquals(destinationRoom.getId(), resultLocation.getLocationId());
        Assert.assertEquals(expedition.getId(), resultLocation.getExpeditionId());
        final var expeditionLoot = dungeonExpeditionItemRepository.findAllByExpeditionId(expedition.getId());
        Assert.assertEquals(1, expeditionLoot.size());
        Assert.assertEquals(treasure.getId(), expeditionLoot.get(0).getItemId());
        final var item = itemRepository.findById(expeditionLoot.get(0).getItemId()).orElseThrow();
        Assert.assertNull(item.getUserId());
        final var updatedEvent = treasureEventRepository.findById(treasureEvent.getId());
        Assert.assertTrue(updatedEvent.isEmpty());
    }
}