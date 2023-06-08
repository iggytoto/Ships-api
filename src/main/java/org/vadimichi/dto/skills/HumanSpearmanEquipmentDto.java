package org.vadimichi.dto.skills;

import lombok.*;
import org.gassangaming.model.skills.human.HumanSpearmanSkills;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HumanSpearmanEquipmentDto extends UnitSkillsDto {
    private int doubleEdgePoints;
    private int midRangePoints;

    public static HumanSpearmanEquipmentDto ofDomain(HumanSpearmanSkills t) {
        final var result = HumanSpearmanEquipmentDto.builder().doubleEdgePoints(t.getDoubleEdgePoints()).midRangePoints(t.getMidRangePoints()).build();
        result.setId(t.getId());
        result.setUnitId(t.getUnitId());
        return result;
    }

    public HumanSpearmanSkills toDomain() {
        final var result = new HumanSpearmanSkills();
        result.setId(id);
        result.setUnitId(unitId);
        result.setMidRangePoints(doubleEdgePoints);
        result.setDoubleEdgePoints(midRangePoints);
        return result;
    }
}
