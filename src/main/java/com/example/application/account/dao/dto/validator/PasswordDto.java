package com.example.application.account.dao.dto.validator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordDto {
    private String oldPassword;

    private  String token;

    @ValidPassword
    private String newPassword;
}
