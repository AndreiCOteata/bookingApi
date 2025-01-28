package com.example.application.config.security.jwt;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

@Getter
public class PathConstants {
    private final List<String> PATH_TO_EXCLUDE = Arrays.asList(
            "/api/v1/auth/sign-up",
            "/api/v1/auth/login",
            "/api/v1/auth/verify",
            "/api/v1/auth/forgot",
            "/api/v1/auth/recovery",
            "/api/v1/auth/resend",
            "/api/v1/auth/refresh-token",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/csrf",
            "/favicon.ico",
            "/api/v1/sendemail",
            "/api/v1/hotel/search"
    );

    @Value("${app.verify.email.success}")
    private String VERIFY_EMAIL_SUCCESS_PATH;
    @Value("${app.verify.email.failed}")
    private String VERIFY_EMAIL_FAILED_PATH;
    @Value("${app.reset.password.path}")
    private String RESET_PASSWORD_PATH;

    public HttpHeaders getUriPath(String path){
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(path));
        return headers;
    }
}
