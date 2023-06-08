package org.vadimichi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.vadimichi.dto.DtoBase;
import org.vadimichi.dto.ErrorResponseDto;
import org.vadimichi.dto.controllers.matchmaking.MatchMakingCancelRegisterRequestDto;
import org.vadimichi.dto.controllers.matchmaking.MatchMakingRegisterRequestDto;
import org.vadimichi.dto.controllers.matchmaking.ServerApplicationRequestDto;
import org.vadimichi.service.UserContext;
import org.vadimichi.service.matchmaking.MatchMakingService;

@RestController
public class MatchMakingController {
    public static final String PATH = "/matchMaking";
    public static final String REGISTER_PATH = "/register";
    public static final String STATUS_PATH = "/status";
    public static final String APPLY_SERVER_PATH = "/applyAsServer";
    public static final String CANCEL_PATH = "/cancel";

    @Autowired
    MatchMakingService matchMakingService;

    @PostMapping(PATH + REGISTER_PATH)
    @Transactional
    public DtoBase register(@RequestBody MatchMakingRegisterRequestDto dto, @RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context) {
        return ErrorResponseDto.Of("not implemented yet");
    }

    @GetMapping(PATH + STATUS_PATH)
    public DtoBase status(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context) {
        return ErrorResponseDto.Of("not implemented yet");
    }

    @PostMapping(PATH + APPLY_SERVER_PATH)
    @Transactional
    public DtoBase apply(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context, @RequestBody ServerApplicationRequestDto dto) {
        return ErrorResponseDto.Of("not implemented yet");
    }

    @PostMapping(PATH + CANCEL_PATH)
    @Transactional
    public DtoBase cancel(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context, @RequestBody MatchMakingCancelRegisterRequestDto dto) {
        return ErrorResponseDto.Of("not implemented yet");
    }

}
