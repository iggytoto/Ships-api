package org.vadimichi.controller;

import org.gassangaming.dto.DtoBase;
import org.gassangaming.dto.ErrorResponseDto;
import org.vadimichi.dto.ListResponseDto;
import org.gassangaming.dto.OkResponseDto;
import org.gassangaming.dto.controllers.events.*;
import org.vadimichi.dto.controllers.events.*;
import org.vadimichi.dto.controllers.events.eventinstanceresult.EventInstanceResultDto;
import org.gassangaming.service.UserContext;
import org.gassangaming.service.event.EventsService;
import org.gassangaming.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
public class EventsController {
    public static final String PATH = "/events";
    public static final String REGISTER_PATH = "/register";
    public static final String STATUS_PATH = "/status";
    public static final String APPLY_SERVER_PATH = "/applyAsServer";
    public static final String SAVE_EVENT_INSTANCE_RESULT = "/saveEventInstanceResult";
    public static final String GET_DATA_PATH = "/getData";
    public static final String CANCEL_PATH = "/cancel";

    @Autowired
    EventsService eventsService;

    @PostMapping(PATH + REGISTER_PATH)
    @Transactional
    public DtoBase register(@RequestBody EventRegisterRequestDto dto, @RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context) {
        try {
            return EventDto.of(eventsService.register(dto.getUnitsIds(), dto.getEventType(), context.getToken().getUserId()));
        } catch (ServiceException se) {
            return ErrorResponseDto.Of(se.getMessage());
        }
    }

    @GetMapping(PATH + STATUS_PATH)
    public DtoBase status(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context) {
        return ListResponseDto.of(eventsService.status(context.getToken().getUserId()).stream().map(EventInstanceDto::of).collect(Collectors.toList()));
    }

    @PostMapping(PATH + APPLY_SERVER_PATH)
    @Transactional
    public DtoBase apply(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context, @RequestBody ServerApplicationRequestDto dto) {
        try {
            return EventInstanceDto.of(eventsService.applyServer(dto.getHost(), dto.getPort(), context.getToken().getUserId()));
        } catch (ServiceException se) {
            return ErrorResponseDto.Of(se.getMessage());
        }
    }

    @PostMapping(PATH + CANCEL_PATH)
    @Transactional
    public DtoBase cancel(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context, @RequestBody EventCancelRequestDto dto) {
        try {
            eventsService.cancel(dto.getEventId(), context.getToken().getUserId());
            return new OkResponseDto();
        } catch (ServiceException se) {
            return ErrorResponseDto.Of(se.getMessage());
        }
    }

    @GetMapping(PATH + GET_DATA_PATH)
    public DtoBase getData(@RequestBody EventInstanceDataRequestDto dto) {
        return EventInstanceDataDto.of(eventsService.getEventInstanceData(dto.getEventInstanceId()));
    }

    @PostMapping(PATH + SAVE_EVENT_INSTANCE_RESULT)
    @Transactional
    public DtoBase save(@RequestAttribute(UserContext.CONTEXT_ATTRIBUTE_NAME) UserContext context, @RequestBody EventInstanceResultDto dto) {
        try {
            eventsService.saveResult(dto.toDomain(), context.getToken().getUserId());
            return new OkResponseDto();
        } catch (ServiceException se) {
            return ErrorResponseDto.Of(se.getMessage());
        }
    }

}
