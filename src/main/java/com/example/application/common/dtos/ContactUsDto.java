package com.example.application.common.dtos;

import com.example.application.account.dao.dto.validator.EmailConstraint;
import com.example.application.common.model.AbstractDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactUsDto extends AbstractDto {
    private static final long serialVersionUID = 6980676104694619475L;

    @Size(min = 2)
    @NotNull
    @JsonProperty(value = "name")
    private String name;

    @EmailConstraint
    @NotNull
    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "message")
    private String message;

}
