package org.vadimichi.dto.controllers.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.event.Event;
import org.gassangaming.model.event.EventStatus;
import org.gassangaming.model.event.EventType;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class EventDto extends DtoBase {

    private long id;
    private EventType eventType;
    private EventStatus status;

    public static EventDto of(Event e) {
        return builder()
                .id(e.getId())
                .eventType(e.getEventType())
                .status(e.getStatus())
                .build();
    }
}
