package com.example.application.config.security.filter;

import com.example.application.common.dtos.ErrorResponse;
import com.example.application.common.dtos.JsonMapper;
import com.example.application.config.security.AllowedHttpMethods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.example.application.common.dtos.CommonErrorResponseType.METHOD_NOT_ALLOWED;

@Slf4j
@Component
public class HttpMethodRestrictingFilter extends OncePerRequestFilter {

    private static final ErrorResponse REJECT_RESPONSE = new ErrorResponse(METHOD_NOT_ALLOWED);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String httpMethod = httpServletRequest.getMethod();
        String requestURI = httpServletRequest.getRequestURI();

        if (httpMethod != null && AllowedHttpMethods.getAllowedMethods().contains(httpMethod.toUpperCase())) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } else {
            String payload = JsonMapper.writeValueAsString(REJECT_RESPONSE);
            httpServletResponse.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            httpServletResponse.setContentType("application/json");
            httpServletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
            httpServletResponse.setHeader("Allowed", String.join(", ", AllowedHttpMethods.getAllowedMethods()));
            httpServletResponse.setContentLength(payload.getBytes(StandardCharsets.UTF_8).length);
            httpServletResponse.getWriter().print(payload);
            httpServletResponse.getWriter().flush();
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Exclude Swagger and public paths
        return path.startsWith("/swagger-ui") || path.startsWith("/v2/api-docs") || path.startsWith("/api/v1/auth");
    }
}