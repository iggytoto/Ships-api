package org.vadimichi.dto.skills;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.unit.UnitType;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpgradeUnitSkillsRequestDto extends DtoBase {
    public long skillsId;
    public UnitType unitType;
    public String paramNameToUpgrade;
}
