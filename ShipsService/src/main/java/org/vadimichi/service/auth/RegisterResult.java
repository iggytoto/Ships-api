package org.vadimichi.service.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResult {
    long userId;
}
