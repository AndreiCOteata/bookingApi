package com.example.application.common.email;

import lombok.Data;

@Data
public class ConfirmationEmail implements EmailMessage{
    private String name;
    private String confirmationUrl;

    @Override
    public String getAsHtml(EmailMapper mapper) {
        return mapper.getBodyEmail(this);
    }

    @Override
    public String getSubject() {
        return "Confirm Registration Email Address";
    }
}
