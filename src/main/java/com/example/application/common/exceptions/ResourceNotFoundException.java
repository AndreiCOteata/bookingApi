package com.example.application.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends BaseAppException{

    private static final long serialVersionUID = -7267963160306970805L;

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
