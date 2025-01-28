package com.example.application.config.security.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChangePasswordRequest extends PasswordResetRequest{
    private String currentPassword;
}
