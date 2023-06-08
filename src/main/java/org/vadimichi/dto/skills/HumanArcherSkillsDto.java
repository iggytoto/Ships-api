package org.vadimichi.dto.skills;

import lombok.*;
import org.gassangaming.model.skills.human.HumanArcherSkills;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HumanArcherSkillsDto extends UnitSkillsDto {

    private int midRangePoints;
    private int longRangePoints;
    private boolean fireArrows;
    private boolean poisonArrows;

    public static HumanArcherSkillsDto ofDomain(HumanArcherSkills t) {
        final var result = HumanArcherSkillsDto.builder().midRangePoints(t.getMidRangePoints()).longRangePoints(t.getLongRangePoints()).fireArrows(t.isFireArrows()).poisonArrows(t.isPoisonArrows()).build();
        result.setId(t.getId());
        result.setUnitId(t.getUnitId());
        return result;
    }
}
