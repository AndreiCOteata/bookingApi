package com.example.application.common.email;

public interface EmailMessage {
    String getAsHtml(EmailMapper mapper);
    String getSubject();
}
