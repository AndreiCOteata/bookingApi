package com.example.application.account.exception;

import com.example.application.common.dtos.ErrorResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TokenErrorResponseType implements ErrorResponseType {

    INVALID_TOKEN("INVALID_TOKEN", "Token is invalid"),
    BAD_CREDENTIALS("BAD_CREDENTIALS", "Invalid credentials"),
    PROFILE_LOCKED("PROFILE_LOCKED", "Locked profile"),
    INVALID_TOKEN_SIGNATURE("INVALID_TOKEN_SIGNATURE", "Invalid token signature"),
    EXPIRED_TOKEN("EXPIRED_TOKEN", "Token is expired");


    private final String code;
    private final String message;
}
