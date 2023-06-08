package org.vadimichi.controller;

import org.vadimichi.dto.controllers.barracks.ChangeUnitBattleBehaviorRequestDto;
import org.vadimichi.dto.controllers.barracks.ChangeUnitNameRequestDto;
import org.vadimichi.dto.controllers.barracks.EquipItemRequestDto;
import org.vadimichi.dto.controllers.barracks.UnEquipItemRequestDto;
import org.vadimichi.dto.items.ItemDto;
import org.vadimichi.dto.unit.UnitDto;
import org.gassangaming.model.item.Item;
import org.gassangaming.model.item.ItemRarity;
import org.gassangaming.model.item.ItemType;
import org.gassangaming.model.unit.BattleBehavior;
import org.gassangaming.model.unit.human.HumanWarrior;
import org.gassangaming.repository.item.ItemRepository;
import org.gassangaming.repository.item.UnitItemsRepository;
import org.gassangaming.repository.unit.UnitRepository;
import org.gassangaming.service.exception.ServiceException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.vadimichi.dto.ListResponseDto;

/**
 * Test case:
 * - Get available units from barrack
 * - change unit name
 * - change unit battle behavior
 * - set unit to training
 * - equip item on unit
 * - unequip item from unit
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BarrackUnitManipulationsTestCase extends UseCaseTestBase {

    private final static String NEW_NAME = "NEW_NAME";

    private final static String LOGIN = "asdasd1d1d1d";
    private final static String PASSWORD = "asdasd1d1d1d";

    @Autowired
    UnitRepository unitRepository;
    @Autowired
    BarrackController barrackController;
    @Autowired
    ItemRepository itemRepository;
    @Autowired
    UnitItemsRepository unitItemsRepository;


    long itemId;
    long unitId;


    @Before
    public void setup() throws ServiceException {
        userId = registerUser(LOGIN, PASSWORD);
        context = login(LOGIN, PASSWORD);
        final var unit1 = new HumanWarrior();
        unit1.setOwnerId(userId);
        unitRepository.save(unit1);
        unitId = unit1.getId();
        final var unit2 = new HumanWarrior();
        unit2.setOwnerId(userId);
        unitRepository.save(unit2);
        final var unit3 = new HumanWarrior();
        unit3.setOwnerId(userId);
        unitRepository.save(unit3);
        itemId = itemRepository.save(new Item(userId, ItemType.Boots, "Woody's boot", ItemRarity.Legendary)).getId();
    }

    @Test
    public void testCase() {
        final var units = ((ListResponseDto<UnitDto>) barrackController.getAvailableUnits(context)).getItems();
        Assert.assertEquals(3, units.size());
        final var unitToChange = units.stream().findFirst().orElseThrow();
        //name change test
        final var changeUnitNameRequestDto = new ChangeUnitNameRequestDto();
        changeUnitNameRequestDto.setUnitId(unitToChange.getId());
        changeUnitNameRequestDto.setNewName(NEW_NAME);
        barrackController.changeUnitName(context, changeUnitNameRequestDto);
        final var changedUnitName = unitRepository.findById(unitToChange.getId()).orElseThrow();
        Assert.assertEquals(NEW_NAME, changedUnitName.getName());
        //battle behavior change test
        final var changeUnitBbRequestDto = new ChangeUnitBattleBehaviorRequestDto();
        changeUnitBbRequestDto.setUnitId(unitToChange.getId());
        changeUnitBbRequestDto.setNewBattleBehavior(BattleBehavior.GuardNearestAlly);
        barrackController.changeUnitBehavior(context, changeUnitBbRequestDto);
        final var changedUnitBb = unitRepository.findById(unitToChange.getId()).orElseThrow();
        Assert.assertEquals(BattleBehavior.GuardNearestAlly, changedUnitBb.getBattleBehavior());
        //equip item
        final var equipItemDto = new EquipItemRequestDto();
        equipItemDto.setItemId(itemId);
        equipItemDto.setUnitId(unitId);
        barrackController.equipItem(context, equipItemDto);
        final var equippedItems = unitItemsRepository.findByUnitId(unitId).stream().toList();
        Assert.assertNotNull(equippedItems);
        Assert.assertEquals(1, equippedItems.size());
        final var updatedUnits = ((ListResponseDto<UnitDto>) barrackController.getAvailableUnits(context)).getItems();
        Assert.assertEquals(3, updatedUnits.size());
        final var equippedUser = updatedUnits.stream().filter(u -> u.getId() == unitId).findFirst().orElseThrow();
        Assert.assertNotNull(equippedUser.getItems());
        Assert.assertEquals(1, equippedUser.getItems().size());
        Assert.assertEquals(userId, equippedUser.getItems().get(0).getUserId());
        Assert.assertEquals(ItemType.Boots, equippedUser.getItems().get(0).getItemType());
        Assert.assertEquals(ItemRarity.Legendary, equippedUser.getItems().get(0).getRarity());
        Assert.assertEquals("Woody's boot", equippedUser.getItems().get(0).getName());
        //unequip item
        final var unequipDto = new UnEquipItemRequestDto();
        unequipDto.setItemId(itemId);
        barrackController.unEquipItem(context, unequipDto);
        final var allItems = (ListResponseDto<ItemDto>) barrackController.getAvailableItems(context);
        Assert.assertNotNull(allItems);
        Assert.assertNotNull(allItems.getItems());
        Assert.assertEquals(1, allItems.getItems().size());
        Assert.assertEquals(itemId, allItems.getItems().stream().findFirst().orElseThrow().getId().longValue());
    }
}
