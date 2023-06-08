package org.vadimichi.service.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import org.apache.commons.lang3.time.DateUtils;
import org.vadimichi.model.Token;
import org.vadimichi.model.User;
import org.vadimichi.repository.TokenRepository;
import org.vadimichi.repository.UserRepository;
import org.vadimichi.service.UserContext;
import org.vadimichi.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Override
    public Token login(String login, String password) throws ServiceException {
        final var u = userRepository.findUserByLogin(login);
        if (u != null) {
            final var passHash = hashString(password);
            if (u.getPasswordSha().equals(passHash)) {
                return tokenRepository.save(getToken(u.getId()));
            } else {
                throw new ServiceException("Incorrect password");
            }
        } else {
            throw new ServiceException("Unknown user");
        }
    }

    private Token getToken(long userId) {
        final var token = tokenRepository.findByUserId(userId);
        final var now = new Date();
        if (token == null) {
            return Token.Of(hashString(now.toString()), userId, DateUtils.addHours(now, 1));
        } else {
            if (token.isPermanent()) {
                return token;
            }
            return Token.Of(token.getId(), hashString(now.toString()), userId, DateUtils.addHours(now, 1));
        }
    }

    @Override
    public long register(String login, String password) throws ServiceException {
        final var u = userRepository.findUserByLogin(login);
        if (u != null) {
            throw new ServiceException("Login already in use");
        } else {
            final var passHash = hashString(password);
            final var registeredUser = userRepository.save(User.PlayerOf(login, passHash));
            return registeredUser.getId();
        }
    }

    @Override
    public UserContext validateToken(String t) throws ServiceException {
        try {
            final var now = new Date();
            final var token = Token.Of(new String(BaseEncoding.base64().decode(t)));
            final var actualToken = tokenRepository.findByUserId(token.getUserId());
            if (actualToken == null) {
                throw new ServiceException("Not authorized");
            }
            if (actualToken.getValue().equals(token.getValue()) && (actualToken.isPermanent() || actualToken.getValidTo().after(now))) {
                actualToken.setValidTo(DateUtils.addHours(now, 1));
                final var updatedToken = tokenRepository.save(actualToken);
                return UserContext.builder().token(updatedToken).build();
            } else {
                throw new ServiceException("Invalid token");
            }
        } catch (JsonProcessingException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private String hashString(String s) {
        return Hashing.sha256().hashString(s, StandardCharsets.UTF_8).toString();
    }
}
