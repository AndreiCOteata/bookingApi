package com.example.application.config.swagger;

import com.example.application.config.security.jwt.JwtAuthenticationEntryPoint;
import com.example.application.config.security.jwt.JwtAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class NonProdWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    private static final int BCRYPT_PASSWORD_ENCODE_STRENGTH = 11;

    private final JwtAuthenticationEntryPoint entryPoint;
    private final JwtAuthorizationFilter authorizationFilter;

    @Autowired
    public NonProdWebSecurityConfigurer(JwtAuthenticationEntryPoint entryPoint,
                                        JwtAuthorizationFilter authorizationFilter) {
        this.entryPoint = entryPoint;
        this.authorizationFilter = authorizationFilter;
    }

    @Bean
    @Primary
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCRYPT_PASSWORD_ENCODE_STRENGTH);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers(
                        "/swagger-ui.html",
                        "/swagger-ui/**",      // Swagger UI static resources
                        "/v3/api-docs/**",     // OpenAPI docs (if using SpringDoc)
                        "/v2/api-docs",        // Swagger 2 API docs
                        "/configuration/ui",   // Swagger configuration
                        "/swagger-resources/**",
                        "/configuration/**",
                        "/webjars/**",
                        "/",
                        "/api/v1/sendemail",
                        "/api/v1/hotel/search"
                        ).permitAll()
                .antMatchers("/api/v1/auth/**").permitAll() // Permit public endpoints
                .anyRequest().authenticated() // Authenticate other requests
                .and()
                .httpBasic()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authorizationFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
