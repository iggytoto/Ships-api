package org.vadimichi.controller;

import org.vadimichi.dto.skills.UpgradeUnitSkillsRequestDto;
import org.gassangaming.model.skills.human.HumanWarriorSkills;
import org.gassangaming.model.unit.UnitType;
import org.gassangaming.model.unit.human.HumanWarrior;
import org.gassangaming.repository.unit.equip.HumanWarriorSkillsRepository;
import org.gassangaming.service.exception.ServiceException;
import org.gassangaming.service.tavern.TavernService;
import org.gassangaming.service.unit.skills.HumanWarriorUnitSkillsServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Test case:
 * - upgrade Human warrior equip to 1,0
 * - upgrade Human warrior equip to 1,1
 * - upgrade Human warrior equip to 1,2
 * - upgrade Human warrior equip to 2,2
 * - upgrade Human warrior equip to 3,2
 * - upgrade Human warrior equip to 4,2
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class HumanWarriorSkillsUpgradeTestCase extends UseCaseTestBase {

    @Autowired
    HumanWarriorSkillsRepository equipmentRepository;

    @Autowired
    BarrackController barrackController;
    @Autowired
    TavernService tavernService;

    HumanWarrior unit;

    @Before
    public void setup() throws ServiceException {
        registerDefaultUser();
        loginAsDefaultUser();
        unit = (HumanWarrior) tavernService.buyUnit(UnitType.HumanWarrior, context.getToken().getUserId());
    }

    @Test
    public void testCase() {
        final var eqId = equipmentRepository.getByUnitId(unit.getId()).getId();
        final var upgradeRequestDto = new UpgradeUnitSkillsRequestDto();
        upgradeRequestDto.setSkillsId(eqId);
        upgradeRequestDto.setUnitType(UnitType.HumanWarrior);
        upgradeRequestDto.setParamNameToUpgrade(HumanWarriorUnitSkillsServiceImpl.DefenceParamName);
        barrackController.upgradeUnitEquipment(context, upgradeRequestDto);
        assertEquipmentOnState(equipmentRepository.getByUnitId(unit.getId()), 1, 0);
        upgradeRequestDto.setParamNameToUpgrade(HumanWarriorUnitSkillsServiceImpl.OffenceParamName);
        barrackController.upgradeUnitEquipment(context, upgradeRequestDto);
        assertEquipmentOnState(equipmentRepository.getByUnitId(unit.getId()), 1, 1);
        upgradeRequestDto.setParamNameToUpgrade(HumanWarriorUnitSkillsServiceImpl.OffenceParamName);
        barrackController.upgradeUnitEquipment(context, upgradeRequestDto);
        assertEquipmentOnState(equipmentRepository.getByUnitId(unit.getId()), 1, 2);
        upgradeRequestDto.setParamNameToUpgrade(HumanWarriorUnitSkillsServiceImpl.DefenceParamName);
        barrackController.upgradeUnitEquipment(context, upgradeRequestDto);
        assertEquipmentOnState(equipmentRepository.getByUnitId(unit.getId()), 2, 2);
        upgradeRequestDto.setParamNameToUpgrade(HumanWarriorUnitSkillsServiceImpl.DefenceParamName);
        barrackController.upgradeUnitEquipment(context, upgradeRequestDto);
        assertEquipmentOnState(equipmentRepository.getByUnitId(unit.getId()), 3, 2);
        upgradeRequestDto.setParamNameToUpgrade(HumanWarriorUnitSkillsServiceImpl.DefenceParamName);
        barrackController.upgradeUnitEquipment(context, upgradeRequestDto);
        assertEquipmentOnState(equipmentRepository.getByUnitId(unit.getId()), 4, 2);
    }

    private void assertEquipmentOnState(HumanWarriorSkills eq, int expectedDefence, int expectedOffence) {
        Assert.assertEquals(expectedDefence, eq.getDefencePoints());
        Assert.assertEquals(expectedOffence, eq.getOffencePoints());
    }
}