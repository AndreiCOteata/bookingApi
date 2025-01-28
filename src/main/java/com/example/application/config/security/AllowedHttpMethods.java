package com.example.application.config.security;

import lombok.Getter;
import org.springframework.http.HttpMethod;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public final class AllowedHttpMethods {

    private AllowedHttpMethods(){
    }

    private static final List<String> ALLOWED_METHODS_AS_STRING =
            List.of(
                HttpMethod.GET,
                HttpMethod.POST,
                HttpMethod.PUT,
                HttpMethod.PATCH,
                HttpMethod.DELETE,
                HttpMethod.OPTIONS
            )
                    .stream()
                    .map(Enum::toString)
                    .collect(Collectors.toList());

    public static List<String> getAllowedMethods() {
        return ALLOWED_METHODS_AS_STRING;
    }
}
