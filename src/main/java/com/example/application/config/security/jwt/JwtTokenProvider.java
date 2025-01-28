package com.example.application.config.security.jwt;

import com.example.application.config.security.model.AccountSecurity;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;

import static java.lang.String.format;

@Component
public class JwtTokenProvider {
    private static final Logger LOG = LoggerFactory.getLogger(JwtTokenProvider.class);

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtRefreshTokenSecret}")
    private String jwtRefreshTokenSecret;

    @Value("${app.jwtExpirationInMs}")
    private Long jwtExpirationInMs;

    @Value("${app.jwtRefreshExpirationInMs}")
    private Long jwtRefreshExpirationInMs;

    public String generateToken(Authentication authentication) {
        AccountSecurity userDetails = (AccountSecurity) authentication.getPrincipal();
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(format("%s", userDetails.getUsername()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateAccessTokenFromRefreshToken(String email) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(format("%s", email))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(format("%s", username))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String generateRefreshToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtRefreshExpirationInMs);
        return Jwts.builder()
                .setSubject(format("%s", username))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshTokenSecret)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public String getUsernameFromRefreshToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtRefreshTokenSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject().split(",")[0];
    }

    public Date getExpirationDate(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getExpiration();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.SignatureException ex) {
            LOG.error("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            LOG.error("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            LOG.error("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            LOG.error("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            LOG.error("JWT string is illegal");
        }
        return false;
    }

    public void validateTokenFromEmail(String authToken)
            throws io.jsonwebtoken.SignatureException,
                MalformedJwtException,
                ExpiredJwtException,
                IllegalArgumentException {
        Jwts.parser().setSigningKey(this.jwtSecret).parseClaimsJws(authToken);
    }

    public void validateRefreshToken(String authToken)
            throws io.jsonwebtoken.SignatureException,
            MalformedJwtException,
            ExpiredJwtException,
            IllegalArgumentException {
        Jwts.parser().setSigningKey(this.jwtRefreshTokenSecret).parseClaimsJws(authToken);
    }
}
