package org.vadimichi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.io.BaseEncoding;
import org.vadimichi.dto.controllers.login.LoginRequestDto;
import org.vadimichi.dto.controllers.login.LoginResponseDto;
import org.vadimichi.dto.controllers.login.RegisterRequestDto;
import org.vadimichi.dto.controllers.login.RegisterResponseDto;
import org.gassangaming.dto.DtoBase;
import org.gassangaming.dto.ErrorResponseDto;
import org.gassangaming.service.auth.AuthService;
import org.gassangaming.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    public static final String PATH = "/auth";

    @Autowired
    private AuthService authService;

    @PostMapping(PATH + "/login")
    public DtoBase login(@RequestBody LoginRequestDto request) {
        try {
            final var token = authService.login(request.getLogin(), request.getPassword());
            return LoginResponseDto.builder().token(BaseEncoding.base64().encode(token.toJson().getBytes())).build();
        } catch (ServiceException | JsonProcessingException ase) {
            return ErrorResponseDto.Of(ase.getMessage());
        }
    }

    @PostMapping(PATH + "/register")
    public DtoBase register(@RequestBody RegisterRequestDto request) {
        try {
            return RegisterResponseDto.builder().userId(authService.register(request.getLogin(), request.getPassword())).build();
        } catch (ServiceException ase) {
            return ErrorResponseDto.Of(ase.getMessage());
        }
    }

}
