package org.vadimichi.dto.controllers.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.model.event.EventType;

import java.util.Collection;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventRegisterRequestDto extends DtoBase {
    Collection<Long> unitsIds;
    EventType eventType;
}
