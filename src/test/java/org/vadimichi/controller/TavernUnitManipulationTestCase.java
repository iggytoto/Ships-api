package org.vadimichi.controller;

import org.vadimichi.dto.controllers.tavern.BuyUnitRequestDto;
import org.vadimichi.dto.ListResponseDto;
import org.vadimichi.dto.unit.UnitDto;
import org.gassangaming.model.unit.UnitType;
import org.gassangaming.repository.unit.UnitRepository;
import org.gassangaming.service.exception.ServiceException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

/**
 * Tavern test case:
 * - show available units
 * - buy unit
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TavernUnitManipulationTestCase extends UseCaseTestBase {

    private static final String LOGIN = "noiunoqgoq";
    private static final String PASSWORD = "noiunoqgoq";

    @Autowired
    TavernController tavernController;
    @Autowired
    UnitRepository unitRepository;

    @Before
    public void setup() throws ServiceException {
        userId = registerUser(LOGIN,PASSWORD);
        context = login(LOGIN,PASSWORD);
    }

    @Test
    public void testCase() {

        //get available units test
        final var getAvailableUnitsResponseDto = ((ListResponseDto<UnitDto>) tavernController.getAvailableUnits()).getItems();
        Assert.isTrue(getAvailableUnitsResponseDto.size() > 0, "Should be available units");
        //buy unit test
        final var buyUnitRequestDto = new BuyUnitRequestDto();
        buyUnitRequestDto.setType(UnitType.HumanWarrior);
        tavernController.buyUnit(buyUnitRequestDto, context);
        final var buyedUnits = unitRepository.findByOwnerId(userId);
        Assert.isTrue(buyedUnits.size() > 0, "Should be buyed units");
    }
}
