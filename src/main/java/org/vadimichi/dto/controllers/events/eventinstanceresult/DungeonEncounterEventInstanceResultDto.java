package org.vadimichi.dto.controllers.events.eventinstanceresult;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.service.event.result.DungeonEncounterEventInstanceResult;
import org.gassangaming.service.event.result.EventInstanceResult;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("DungeonEncounter")
public class DungeonEncounterEventInstanceResultDto extends EventInstanceResultDto {
    private long dungeonEncounterEventId;
    private List<Long> encounteredExpeditions = new ArrayList<>();

    @Override
    public EventInstanceResult toDomain() {
        final var result = new DungeonEncounterEventInstanceResult();
        result.setEventInstanceId(getEventInstanceId());
        result.setEventType(getEventType());
        result.setUnitsHitPoints(getUnitsHitPoints());
        result.setDungeonEncounterEventId(getDungeonEncounterEventId());
        result.setEncounteredExpeditions(getEncounteredExpeditions());
        return result;
    }
}
