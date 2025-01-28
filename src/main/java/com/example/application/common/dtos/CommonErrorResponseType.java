package com.example.application.common.dtos;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CommonErrorResponseType  implements ErrorResponseType{

    FORBIDDEN("FORBIDDEN", HttpStatus.FORBIDDEN.getReasonPhrase()),
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED.getReasonPhrase()),
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase()),
    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND.getReasonPhrase()),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()),
    RESOURCE_ALREADY_EXISTS("RESOURCE_ALREADY_EXISTS", "The provided resource already exists!"),
    INVALID_PATH_RESOURCE("INVALID_PATH_RESOURCE", "The provided path resource is invalid"),
    INVALID_BODY("INVALID_BODY","Invalid body"),
    BAD_REQUEST("BAD_REQUEST",HttpStatus.BAD_REQUEST.getReasonPhrase()),
    CONFLICT("CONFLICT", HttpStatus.CONFLICT.getReasonPhrase());

    private final String code;
    private final String message;

    CommonErrorResponseType(String code, String message){
        this.code = code;
        this.message = message;
    }
}
