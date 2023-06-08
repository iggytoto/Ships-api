package org.vadimichi.controller;

import org.vadimichi.dto.ListResponseDto;
import org.gassangaming.dto.controllers.dungeon.*;
import org.gassangaming.model.dungeon.DungeonInstance;
import org.gassangaming.model.dungeon.DungeonPath;
import org.gassangaming.model.dungeon.DungeonRoom;
import org.gassangaming.model.unit.Unit;
import org.gassangaming.model.unit.human.HumanWarrior;
import org.gassangaming.repository.dungeon.*;
import org.gassangaming.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vadimichi.dto.controllers.dungeon.*;

import java.util.ArrayList;

/**
 * test case:
 * - register to the dungeon with roster
 * - move from first room to another
 * - return to town roster
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MoveThroughEmptyDungeonTestCase extends UseCaseTestBase {

    public static final String LOGIN = "hgbnqignqg";
    public static final String PASSWORD = "hgbnqignqg";

    @Autowired
    DungeonInstanceExpeditionLocationRepository dungeonInstanceExpeditionLocationRepository;
    @Autowired
    DungeonInstanceRepository dungeonInstanceRepository;
    @Autowired
    DungeonRoomRepository dungeonRoomRepository;
    @Autowired
    DungeonPathRepository dungeonPathRepository;
    @Autowired
    DungeonController controller;
    @Autowired
    DungeonExpeditionRepository dungeonExpeditionRepository;
    @Autowired
    DungeonExpeditionUnitsRepository dungeonExpeditionUnitsRepository;

    private ArrayList<Unit> roster = new ArrayList<>();
    private long dungeonInstanceId;
    private long pathId;

    @Before
    public void setup() throws ServiceException {
        userId = registerUser(LOGIN, PASSWORD);
        context = login(LOGIN, PASSWORD);
        roster.add(addUnit(new HumanWarrior(), userId));
        roster.add(addUnit(new HumanWarrior(), userId));
        roster.add(addUnit(new HumanWarrior(), userId));

        final var di = dungeonInstanceRepository.save(new DungeonInstance());
        dungeonInstanceId = di.getId();
        final var startRoom = new DungeonRoom(true, di);
        dungeonRoomRepository.save(startRoom);
        final var endRoom = new DungeonRoom(true, di);
        dungeonRoomRepository.save(endRoom);
        final var path = new DungeonPath(startRoom.getId(), endRoom.getId(), di, 1);
        dungeonPathRepository.save(path);
        pathId = path.getId();
    }

    @Test
    public void testCase() {
        //get dungeons
        final var dungeonList = ((ListResponseDto<DungeonInstanceDto>) controller.getDungeons()).getItems();
        Assert.assertTrue(dungeonList.stream().anyMatch(d -> d.getId() == dungeonInstanceId));
        final var d = dungeonList.stream().filter(dd -> dd.getId() == dungeonInstanceId).findFirst().orElseThrow();
        Assert.assertEquals(dungeonInstanceId, d.getId());
        Assert.assertEquals(2, d.getRooms().size());
        final var startingRoom = d.getRooms().get(0);
        // register
        final var expeditionDto = (DungeonExpeditionDto) controller.create(context, new CreateExpeditionRequestDto(dungeonInstanceId, startingRoom.getId(), roster.stream().map(Unit::getId).toList()));
        final var e = dungeonExpeditionRepository.findById(expeditionDto.getId()).orElseThrow();
        Assert.assertEquals(dungeonInstanceId, e.getDungeonInstanceId());
        Assert.assertEquals(userId, e.getUserId());
        final var r = dungeonExpeditionUnitsRepository.findAllByExpeditionId(e.getId());
        Assert.assertEquals(3, r.size());
        final var l = dungeonInstanceExpeditionLocationRepository.findById(e.getId()).orElseThrow();
        Assert.assertEquals(startingRoom.getId(), l.getLocationId());
        Assert.assertTrue(e.getLocation().isRoom());
        //move
        controller.moveExpedition(context, new MoveExpeditionRequestDto(d.getPaths().get(0).getId(), expeditionDto.getId()));
        var location = dungeonInstanceExpeditionLocationRepository.findById(e.getId()).orElseThrow();
        Assert.assertFalse(location.isRoom());
        Assert.assertEquals(pathId, location.getLocationId());
        //background server movement
        location.setRoom(true);
        location.setLocationId(d.getRooms().get(1).getId());
        dungeonInstanceExpeditionLocationRepository.save(location);
        //return expedition
        controller.returnExpedition(context, new ReturnExpeditionRequestDto(expeditionDto.getId()));
        Assert.assertTrue(dungeonExpeditionRepository.findById(expeditionDto.getId()).isEmpty());
        Assert.assertTrue(dungeonExpeditionUnitsRepository.findAllByExpeditionId(expeditionDto.getId()).isEmpty());
        Assert.assertTrue(dungeonInstanceExpeditionLocationRepository.findById(expeditionDto.getId()).isEmpty());


    }
}
