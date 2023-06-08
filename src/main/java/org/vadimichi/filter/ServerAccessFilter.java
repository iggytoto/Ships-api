package org.vadimichi.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.vadimichi.controller.MatchMakingController;
import org.vadimichi.repository.UserRepository;
import org.vadimichi.service.Role;
import org.vadimichi.service.UserContext;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ServerAccessFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final var context = (UserContext) request.getAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME);
        final var user = userRepository.findById(context.getToken().getUserId()).orElseThrow(IllegalStateException::new);
        if (!Role.Server.equals(user.getRole()) && !Role.Admin.equals(user.getRole())) {
            FilterUtils.writeErrorResponse(response, HttpStatus.UNAUTHORIZED, "");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return
                !request.getRequestURI().contains(MatchMakingController.PATH + MatchMakingController.APPLY_SERVER_PATH) &&
                        !request.getRequestURI().contains(MatchMakingController.PATH + MatchMakingController.SAVE_EVENT_INSTANCE_RESULT) &&
                        !request.getRequestURI().contains(MatchMakingController.PATH + MatchMakingController.GET_DATA_PATH);
    }
}
