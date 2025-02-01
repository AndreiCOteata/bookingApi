package com.example.application.company.dao.dto;


import com.example.application.common.model.AbstractDto;
import com.example.application.account.dao.dto.validator.EmailConstraint;
import com.example.application.company.dao.dto.validator.VatIdConstraint;
import com.example.application.location.city.City;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor(force = true)
public class CompanyDto extends AbstractDto {

    private static final long serialVersionUID = 8540873644833841380L;

    @NotBlank
    private Long id;

    @NotBlank
    private String companyName;

    @NotBlank
    @VatIdConstraint
    private String vatId;


    @NonNull
    @Email
    @EmailConstraint
    private String email;

    @NonNull
    private City city;


    @NonNull
    private String details;

    private boolean isActive;

}
