package com.example.application.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerException extends BaseAppException{

    private static final long serialVersionUID = -5815455474221858847L;

    public InternalServerException(String message) {
        super(message);
    }
}
