package org.vadimichi.service.auth;

import lombok.Data;

@Data
public class RegistrationResult {
    String token;
    int userId;
}
