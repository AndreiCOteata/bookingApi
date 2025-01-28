package com.example.application.common.exceptions;

import com.example.application.account.exception.AccountNotFoundException;
import com.example.application.common.dtos.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import javax.naming.ServiceUnavailableException;

import static com.example.application.common.dtos.CommonErrorResponseType.METHOD_NOT_ALLOWED;
import static com.example.application.common.dtos.CommonErrorResponseType.NOT_FOUND;
import static com.example.application.common.dtos.CommonErrorResponseType.BAD_REQUEST;
import static com.example.application.common.dtos.CommonErrorResponseType.SERVICE_UNAVAILABLE;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_NOT_FOUND;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return super.handleExceptionInternal(ex, errorResponse, headers, status, request);
    }

    @Override
    public ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request){
        ErrorResponse response = new ErrorResponse(NOT_FOUND);
        headers.setContentType(MediaType.APPLICATION_JSON);
        return super.handleExceptionInternal(ex, response, headers, status, request);
    }

    @ExceptionHandler({RequestRejectedException.class})
    public ResponseEntity<Object> handleRequestRejectedException(final RequestRejectedException ex, final WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST);
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST,request);
    }

    @ExceptionHandler({ServiceUnavailableException.class})
    public ResponseEntity<Object> handleRuntimeException(final ServiceUnavailableException ex, final WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(SERVICE_UNAVAILABLE);
        return super.handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.SERVICE_UNAVAILABLE,request);
    }

    @ExceptionHandler({AccountNotFoundException.class})
    public ResponseEntity<Object> handleAccountNotFoundException(final AccountNotFoundException ex, final WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(), ACCOUNT_NOT_FOUND.getMessage());
        return super.handleExceptionInternal(ex, errorResponse, contentTypeApplicationHeaders(), HttpStatus.NOT_FOUND,request);
    }

    private HttpHeaders contentTypeApplicationHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

}
