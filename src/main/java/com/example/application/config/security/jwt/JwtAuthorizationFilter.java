package com.example.application.config.security.jwt;

import com.example.application.config.security.CustomUserDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    public static final String HEADER_AUTHORIZATION = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    private final JwtTokenProvider tokenProvider;

    private final CustomUserDetailsService customUserDetailsService;

    private static final Logger LOG = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

    @Autowired
    public JwtAuthorizationFilter(JwtTokenProvider tokenProvider, @Lazy CustomUserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.customUserDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (checkHeaderAuthorization(request)) {
                if (shouldNotFilter(request)) {
                    filterChain.doFilter(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid Authorization header");
                }
                return;
            }

            String authHeader = request.getHeader(HEADER_AUTHORIZATION);
            String accessToken = null;

            if (StringUtils.hasText(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
                accessToken = authHeader.replace(TOKEN_PREFIX, "");
            }

            if (!StringUtils.hasText(accessToken) || !tokenProvider.validateToken(accessToken)) {
                filterChain.doFilter(request, response);
                return;
            }

            String username = tokenProvider.getUsername(accessToken);
            LOG.debug("Retrieved username from JWT: {}", username);

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {
            LOG.error("Could not set user authentication in security context", ex);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication error: " + ex.getMessage());
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean checkHeaderAuthorization(HttpServletRequest request){
        String authenticationHeader = request.getHeader(HEADER_AUTHORIZATION);
        return authenticationHeader == null || !authenticationHeader.startsWith(TOKEN_PREFIX);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.startsWith("/swagger-ui") ||
                path.startsWith("/v2/api-docs") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-resources") ||
                path.startsWith("/webjars") ||
                path.startsWith("/api/v1/auth") ||
                path.startsWith("/api/v1/sendemail") ||
                path.startsWith("/api/v1/hotel/search");
    }
}
