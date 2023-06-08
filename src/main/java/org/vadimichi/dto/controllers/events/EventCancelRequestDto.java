package org.vadimichi.dto.controllers.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
public class EventCancelRequestDto extends DtoBase {
    long eventId;
}
