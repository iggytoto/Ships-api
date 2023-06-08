package org.vadimichi.dto.skills;

import lombok.*;
import org.gassangaming.model.skills.human.HumanClericSkills;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class HumanClericEquipmentDto extends UnitSkillsDto {

    int disciplinePoints;
    boolean shatter;
    boolean divine;
    boolean purge;

    public static HumanClericEquipmentDto ofDomain(HumanClericSkills t) {
        final var result = HumanClericEquipmentDto
                .builder()
                .disciplinePoints(t.getDisciplinePoints())
                .shatter(t.isShatter())
                .divine(t.isDivine())
                .purge(t.isPurge())
                .build();
        result.setId(t.getId());
        result.setUnitId(t.getUnitId());
        return result;
    }

    public HumanClericSkills toDomain() {
        final var result = new HumanClericSkills();
        result.setId(id);
        result.setUnitId(unitId);
        result.setPurge(purge);
        result.setShatter(shatter);
        result.setDivine(divine);
        result.setDisciplinePoints(disciplinePoints);
        return result;
    }
}
