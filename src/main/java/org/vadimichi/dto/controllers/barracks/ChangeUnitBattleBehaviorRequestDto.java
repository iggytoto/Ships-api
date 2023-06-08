package org.vadimichi.dto.controllers.barracks;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.unit.BattleBehavior;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChangeUnitBattleBehaviorRequestDto extends DtoBase {
    long unitId;
    BattleBehavior newBattleBehavior;
}
