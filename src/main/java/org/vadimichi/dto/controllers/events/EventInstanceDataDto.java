package org.vadimichi.dto.controllers.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.vadimichi.dto.unit.UnitDto;
import org.gassangaming.model.event.EventType;
import org.gassangaming.service.event.EventInstanceData;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class EventInstanceDataDto extends DtoBase {

    private List<UnitDto> eventParticipants;
    private long eventInstanceId;
    private EventType eventType;

    public static EventInstanceDataDto of(EventInstanceData domain) {
        return builder().eventParticipants(domain.getEventParticipants().stream().map(UnitDto::of).toList()).eventType(domain.getEventType()).eventInstanceId(domain.getEventInstanceId()).build();
    }
}
