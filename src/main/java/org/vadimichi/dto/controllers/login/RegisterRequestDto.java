package org.vadimichi.dto.controllers.login;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.vadimichi.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterRequestDto extends DtoBase {
    String login;
    String password;
}
