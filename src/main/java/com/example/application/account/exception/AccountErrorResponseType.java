package com.example.application.account.exception;

import com.example.application.common.dtos.ErrorResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountErrorResponseType implements ErrorResponseType {

    ACCOUNT_ALREADY_EXISTS("ACCOUNT_ALREADY_EXISTS", "Invalid Email address"),
    ACCOUNT_DISABLED("ACCOUNT_DISABLED", "Account has been disabled!"),
    ACCOUNT_NOT_FOUND("ACCOUNT_NOT_FOUND", HttpStatus.NOT_FOUND.getReasonPhrase()),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()),
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()),
    INVALID_BODY("INVALID_BODY","Invalid body"),
    ACCOUNT_LOCKED("ACCOUNT_LOCKED", "Locked account"),
    ACCOUNT_NOT_VERIFIED("ACCOUNT_NOT_VERIFIED", "Account hasn't been verified"),
    ACCOUNT_ALREADY_VERIFIED("ACCOUNT_ALREADY_VERIFIED", "Account has already been verified");

    private final String code;
    private final String message;
}
