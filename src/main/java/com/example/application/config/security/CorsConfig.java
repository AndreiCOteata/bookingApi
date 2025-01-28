package com.example.application.config.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class CorsConfig {

    @Value("#{'${cors.allowed-origins}'.split(',')}")
    private Set<String> allowedOrigins;
    @Value("#{'${cors.allowed-headers}'.split(',')}")
    private Set<String> allowedHeaders;

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(toListOrEmpty(allowedOrigins));
        configuration.setAllowedMethods(AllowedHttpMethods.getAllowedMethods());
        configuration.setAllowedHeaders(toListOrEmpty(allowedHeaders));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private List<String> toListOrEmpty(Collection<String> strings){
        if(strings==null){
            return Collections.emptyList();
        }

        return strings.stream()
                .map(String::trim)
                .filter(s->!s.isBlank())
                .collect(Collectors.toList());
    }
}
