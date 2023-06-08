package org.vadimichi.dto.controllers.events.eventinstanceresult;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.event.EventType;
import org.gassangaming.service.event.result.EventInstanceResult;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "eventType", visible = true)
@JsonSubTypes({@JsonSubTypes.Type(PhoenixRaidEventInstanceResultDto.class)})
public abstract class EventInstanceResultDto extends DtoBase {
    protected long eventInstanceId;
    protected EventType eventType;
    protected HashMap<Long, Integer> unitsHitPoints;

    public abstract EventInstanceResult toDomain();
}
