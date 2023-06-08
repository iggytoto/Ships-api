package org.vadimichi.dto.skills;

import lombok.Getter;
import lombok.Setter;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.skills.UnitSkills;
import org.gassangaming.model.skills.human.HumanArcherSkills;
import org.gassangaming.model.skills.human.HumanClericSkills;
import org.gassangaming.model.skills.human.HumanSpearmanSkills;
import org.gassangaming.model.skills.human.HumanWarriorSkills;


@Getter
@Setter
public abstract class UnitSkillsDto extends DtoBase {

    protected Long id;
    protected long unitId;

    public static UnitSkillsDto of(UnitSkills eq) {
        if (eq == null) {
            return null;
        }
        if (eq instanceof HumanWarriorSkills) {
            return HumanWarriorEquipmentDto.ofDomain((HumanWarriorSkills) eq);
        } else if (eq instanceof HumanArcherSkills) {
            return HumanArcherSkillsDto.ofDomain((HumanArcherSkills) eq);
        } else if (eq instanceof HumanSpearmanSkills) {
            HumanSpearmanEquipmentDto.ofDomain((HumanSpearmanSkills) eq);
        } else if (eq instanceof HumanClericSkills) {
            HumanClericEquipmentDto.ofDomain((HumanClericSkills) eq);
        }
        throw new IllegalStateException();
    }
}
