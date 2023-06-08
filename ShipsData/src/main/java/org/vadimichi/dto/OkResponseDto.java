package org.vadimichi.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;

@Builder
@EqualsAndHashCode(callSuper = true)
public class OkResponseDto extends DtoBase {
    public OkResponseDto() {
        super(0L, null);
    }
}
