package org.vadimichi.service.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Service exceptions determines called soft exceptions, that can be send to the client. Example: player do not have
 * money to buy unit - service exception, operation and its transaction is cancelled but this is part of business logic
 * and client should be notified.
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServiceException extends Exception {

    public ServiceException(String message) {
        super(message);
    }
}
