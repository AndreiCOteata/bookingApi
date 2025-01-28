package com.example.application.config.security.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class PasswordResetRequest extends ResetPassword {

    @NotNull
    private String token;
}
