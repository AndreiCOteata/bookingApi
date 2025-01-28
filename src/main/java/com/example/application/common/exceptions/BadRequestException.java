package com.example.application.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends BaseAppException{

    private static final long serialVersionUID = 7840145005138619126L;

    public BadRequestException(String message) {
        super(message);
    }
}
