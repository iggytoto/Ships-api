package org.vadimichi.dto.controllers.events;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.gassangaming.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
public class ServerApplicationRequestDto extends DtoBase {
    private String host;
    private String port;
}
