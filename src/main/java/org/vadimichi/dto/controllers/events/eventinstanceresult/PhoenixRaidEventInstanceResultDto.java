package org.vadimichi.dto.controllers.events.eventinstanceresult;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.service.event.result.PhoenixRaidEventInstanceResult;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeName("PhoenixRaid")
public class PhoenixRaidEventInstanceResultDto extends EventInstanceResultDto {

    @Override
    public PhoenixRaidEventInstanceResult toDomain() {
        final var result = new PhoenixRaidEventInstanceResult();
        result.setEventInstanceId(getEventInstanceId());
        result.setEventType(getEventType());
        result.setUnitsHitPoints(getUnitsHitPoints());
        return result;
    }
}
