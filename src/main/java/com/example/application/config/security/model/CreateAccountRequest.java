package com.example.application.config.security.model;

import com.example.application.account.dao.dto.validator.EmailConstraint;
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
public class CreateAccountRequest extends ResetPassword {
    private static final long serialVersionUID = -6398715484127355003L;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    @EmailConstraint
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

}
