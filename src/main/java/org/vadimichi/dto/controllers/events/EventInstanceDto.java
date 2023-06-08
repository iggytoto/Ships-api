package org.vadimichi.dto.controllers.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.event.EventInstance;
import org.gassangaming.model.event.EventInstanceStatus;
import org.gassangaming.model.event.EventType;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class EventInstanceDto extends DtoBase {

    private long id;
    private long eventId;
    private String host;
    private String port;
    private EventInstanceStatus status;
    private EventType eventType;

    public static EventInstanceDto of(EventInstance e) {
        if (e == null) {
            return null;
        }
        return builder()
                .id(e.getId())
                .eventId(e.getEventId())
                .host(e.getHost())
                .port(e.getPort())
                .status(e.getStatus())
                .eventType(e.getEventType())
                .build();
    }
}
