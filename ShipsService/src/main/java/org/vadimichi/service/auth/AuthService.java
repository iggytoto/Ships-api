package org.vadimichi.service.auth;

import org.vadimichi.model.Token;
import org.vadimichi.service.UserContext;
import org.vadimichi.service.exception.ServiceException;
import org.springframework.stereotype.Service;

/**
 * Service that provides the functionality of registering new users and login to the system.
 */
@Service
public interface AuthService {

    /**
     * Logins and provides the token update
     *
     * @param login    login
     * @param password password
     * @return updated token
     * @throws ServiceException in case login failed
     */
    Token login(String login, String password) throws ServiceException;

    /**
     * Registers the use
     *
     * @param login    login
     * @param password password
     * @return userId in case everythis is ok
     * @throws ServiceException in case register failed
     */
    long register(String login, String password) throws ServiceException;

    /**
     * Validates given token, if its valid prolongs its validation to +1 hour.
     *
     * @param token given token
     * @throws ServiceException in case validation failed
     */
    UserContext validateToken(String token) throws ServiceException;
}
