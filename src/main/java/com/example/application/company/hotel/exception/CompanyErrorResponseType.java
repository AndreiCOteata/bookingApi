package com.example.application.company.hotel.exception;

import com.example.application.common.dtos.ErrorResponseType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CompanyErrorResponseType implements ErrorResponseType {

    COMPANY_NOT_FOUND("COMPANY_NOT_FOUND", HttpStatus.NOT_FOUND.getReasonPhrase()),
    HOTEL_NOT_FOUND("HOTEL_NOT_FOUND", HttpStatus.NOT_FOUND.getReasonPhrase()),
    COMPANY_ALREADY_EXISTS("COMPANY_ALREADY_EXISTS", HttpStatus.CONFLICT.getReasonPhrase());

    private final String code;
    private final String message;
}
