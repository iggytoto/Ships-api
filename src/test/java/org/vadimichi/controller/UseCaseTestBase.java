package org.vadimichi.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.vadimichi.repository.UserRepository;
import org.vadimichi.service.Role;
import org.vadimichi.service.UserContext;
import org.vadimichi.service.auth.AuthService;
import org.vadimichi.service.exception.ServiceException;

public class UseCaseTestBase {

    protected final static String LOGIN = "HAHAHAHAHAH";
    protected final static String PASSWORD = "12312515sdfwdfwef_2rffs";

    protected long userId;

    protected UserContext context;

    @Autowired
    AuthService authService;
    @Autowired
    UserRepository userRepository;


    protected long registerServerUser(String login, String password) throws ServiceException {
        final var id = registerUser(login, password);
        final var u = userRepository.findById(id).orElseThrow();
        u.setRole(Role.Server);
        userRepository.save(u);
        return id;
    }


    protected long registerUser(String login, String password) throws ServiceException {
        return authService.register(login, password);
    }

    protected void registerDefaultUser() throws ServiceException {
        userId = registerUser(LOGIN, PASSWORD);
    }


    protected UserContext login(String login, String password) throws ServiceException {
        return UserContext.builder().token(authService.login(login, password)).build();
    }

    protected void loginAsDefaultUser() throws ServiceException {
        context = login(LOGIN, PASSWORD);
    }
}
