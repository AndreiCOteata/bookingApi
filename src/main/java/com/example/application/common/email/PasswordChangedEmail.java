package com.example.application.common.email;

import lombok.Data;

@Data
public class PasswordChangedEmail implements EmailMessage{
    private String name;
    private String frontendBaseUrl;

    @Override
    public String getAsHtml(EmailMapper mapper) {
        return mapper.getBodyEmail(this);
    }

    @Override
    public String getSubject() {
        return "Password Changed";
    }
}
