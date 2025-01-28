package com.example.application.common.email;

import lombok.Data;

@Data
public class ForgotPasswordEmail implements EmailMessage{
    private String name;
    private String forgotPasswordUrl;


    @Override
    public String getAsHtml(EmailMapper mapper) {
        return mapper.getBodyEmail(this);
    }

    @Override
    public String getSubject() {
        return "Password Change Request";
    }
}
