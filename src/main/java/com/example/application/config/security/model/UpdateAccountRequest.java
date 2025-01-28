package com.example.application.config.security.model;

import com.example.application.location.address.dao.dto.AddressDto;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class UpdateAccountRequest {

    @NotBlank
    private String firstName;
    private String lastName;

    private AddressDto addressDto;
}