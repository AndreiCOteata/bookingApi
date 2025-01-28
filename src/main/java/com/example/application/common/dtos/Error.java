package com.example.application.common.dtos;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "Standard error response fields")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Error {
    private String code;
    private String message;
}
