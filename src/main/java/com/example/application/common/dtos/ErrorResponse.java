package com.example.application.common.dtos;

import io.swagger.annotations.ApiModel;
import lombok.*;

@ApiModel(description = "Standard error response")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private Error error;

    public ErrorResponse(ErrorResponseType errorResponseType){
        this(errorResponseType.getCode(), errorResponseType.getMessage());
    }

    public ErrorResponse(String code, String message){
        this.error = new Error(code, message);
    }
}
