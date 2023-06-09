package org.vadimichi.dto.controllers.login;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vadimichi.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
public class RegisterResponseDto extends DtoBase {
    long userId;
}
