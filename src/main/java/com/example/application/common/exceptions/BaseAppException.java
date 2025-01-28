package com.example.application.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

public class BaseAppException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = -801141965175487315L;

    public BaseAppException(String message){
        super(message);
    }

    public HttpStatus getHttpStatus() {
        ResponseStatus annotation = this.getClass().getAnnotation(ResponseStatus.class);
        if(annotation != null) {
            return annotation.value();
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

}
