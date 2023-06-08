package org.vadimichi.dto.controllers.tavern;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.unit.UnitType;

@EqualsAndHashCode(callSuper = true)
@Data
public class BuyUnitRequestDto extends DtoBase {
    UnitType type;
}
