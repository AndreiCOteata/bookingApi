package com.example.application.common.email;

import lombok.Data;

@Data
public class ContactUsEmail implements EmailMessage {

    private String name;
    private String email;
    private String message;

    @Override
    public String getAsHtml(EmailMapper mapper) {
        return mapper.getBodyEmail(this);
    }

    @Override
    public String getSubject() {
        return "ContactUs";
    }
}
