package com.example.application.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ChangePasswordException extends BaseAppException{
    private static final long serialVersionUID = 4607387706071432401L;

    public ChangePasswordException(String message) {
        super(message);
    }
}
