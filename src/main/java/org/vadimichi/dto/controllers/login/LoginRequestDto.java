package org.vadimichi.dto.controllers.login;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.vadimichi.dto.DtoBase;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto extends DtoBase {

    String login;
    String password;
}
