package com.example.application.config.security.jwt.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class JWTResponse implements Serializable {

    private static final long serialVersionUID = 19273198471319L;

    private final String user;
    private final String accessToken;
    private final String refreshToken;
}
