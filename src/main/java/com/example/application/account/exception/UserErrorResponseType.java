package com.example.application.account.exception;

import com.example.application.common.dtos.ErrorResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserErrorResponseType implements ErrorResponseType {
    INVALID_USERNAME_OR_PASSWORD("INVALID_USERNAME_OR_PASSWORD", "Invalid username or password"),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Invalid Credentials");

    private final String code;
    private final String message;
}
