package com.example.application.common.email;

import com.example.application.config.security.jwt.JwtTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailUtils {

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public EmailUtils(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public String createUrlConfirmation(String email, String baseUrl){
        return String.format("%s?token=%s&email=%s",baseUrl, tokenProvider.generateToken(email), email);
    }

    public String createResetPasswordEmailUrlConfirmation(String email, String baseUrl){
        return String.format("%s?token=%s",baseUrl, tokenProvider.generateToken(email));
    }
}
