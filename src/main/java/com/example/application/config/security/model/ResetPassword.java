package com.example.application.config.security.model;

import com.example.application.account.dao.dto.validator.PasswordMatches;
import com.example.application.account.dao.dto.validator.ValidPassword;
import com.example.application.common.model.AbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@PasswordMatches
public class ResetPassword extends AbstractDto {

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;
}
