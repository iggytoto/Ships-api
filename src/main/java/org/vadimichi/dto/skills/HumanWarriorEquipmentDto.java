package org.vadimichi.dto.skills;

import lombok.*;
import org.gassangaming.model.skills.human.HumanWarriorSkills;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HumanWarriorEquipmentDto extends UnitSkillsDto {
    private int defencePoints;
    private int offencePoints;

    public static HumanWarriorEquipmentDto ofDomain(HumanWarriorSkills t) {
        final var result = HumanWarriorEquipmentDto.builder().defencePoints(t.getDefencePoints()).offencePoints(t.getOffencePoints()).build();
        result.setId(t.getId());
        result.setUnitId(t.getUnitId());
        return result;
    }

    public HumanWarriorSkills toDomain() {
        final var result = new HumanWarriorSkills();
        result.setId(id);
        result.setUnitId(unitId);
        result.setDefencePoints(defencePoints);
        result.setOffencePoints(offencePoints);
        return result;
    }
}
