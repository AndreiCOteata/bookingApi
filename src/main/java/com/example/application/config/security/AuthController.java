package com.example.application.config.security;

import com.example.application.account.dao.dto.AccountDto;
import com.example.application.account.exception.*;
import com.example.application.account.service.AccountService;
import com.example.application.common.controller.BaseController;
import com.example.application.common.dtos.ErrorResponse;
import com.example.application.common.email.NotificationService;
import com.example.application.config.security.jwt.JwtTokenProvider;
import com.example.application.config.security.jwt.request.JWTRequest;
import com.example.application.config.security.jwt.response.JWTResponse;
import com.example.application.config.security.model.ChangePasswordRequest;
import com.example.application.config.security.model.CreateAccountRequest;
import com.example.application.config.security.model.PasswordResetRequest;
import com.example.application.config.security.model.dto.VerificationEmailDto;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountLockedException;
import javax.validation.Valid;
import javax.validation.ValidationException;

import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_DISABLED;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_NOT_FOUND;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_ALREADY_EXISTS;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_ALREADY_VERIFIED;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_NOT_VERIFIED;
import static com.example.application.account.exception.AccountErrorResponseType.ACCOUNT_LOCKED;
import static com.example.application.account.exception.UserErrorResponseType.BAD_CREDENTIALS;
import static com.example.application.common.dtos.CommonErrorResponseType.UNAUTHORIZED;
import static com.example.application.common.dtos.CommonErrorResponseType.CONFLICT;
import static com.example.application.common.dtos.CommonErrorResponseType.SERVICE_UNAVAILABLE;
import static com.example.application.account.exception.TokenErrorResponseType.INVALID_TOKEN_SIGNATURE;
import static com.example.application.account.exception.TokenErrorResponseType.INVALID_TOKEN;
import static com.example.application.account.exception.TokenErrorResponseType.EXPIRED_TOKEN;

@RestController
@Slf4j
public class AuthController extends BaseController {

    private final String API = "/auth";

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenUtil;
    private final AccountService userService;
    private final NotificationService notificationService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenUtil, AccountService userService, NotificationService notificationService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @PostMapping(value = API + "/login",
            consumes = "application/json",
            produces = "application/json")
    public ResponseEntity<Object> login(@RequestBody @Valid JWTRequest request) {
        try {
            userService.allowLogin(request.getUsername());
            Authentication authenticate = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                            ));
            return ResponseEntity.ok(new JWTResponse(request.getUsername(),
                    jwtTokenUtil.generateToken(authenticate),
                    jwtTokenUtil.generateRefreshToken(request.getUsername())));
        } catch (BadCredentialsException ex) {
            try {
                userService.failedLogin(request.getUsername());
            } catch (AccountNotFoundException | AccountDisabledException e) {
                LOGGER.warn(e.getMessage());
            }
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(BAD_CREDENTIALS.getCode(),
                    BAD_CREDENTIALS.getMessage()));
        } catch (AccountDisabledException ex){
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ACCOUNT_DISABLED.getCode(),
                    ACCOUNT_DISABLED.getMessage()));
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch (AccountNotVerifiedException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ACCOUNT_NOT_VERIFIED.getCode(),
                    ACCOUNT_NOT_VERIFIED.getMessage()));
        } catch (AccountLockedException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(ACCOUNT_LOCKED.getCode(),
                    ex.getMessage()));
        }
    }

    @PostMapping(value = API + "/refresh-token", produces = "application/json")
    public ResponseEntity<Object> refreshToken(@RequestParam String refreshToken){
        try{
            jwtTokenUtil.validateRefreshToken(refreshToken);
            String email = jwtTokenUtil.getUsernameFromRefreshToken(refreshToken);
            return ResponseEntity.ok(new JWTResponse(email,
                    jwtTokenUtil.generateAccessTokenFromRefreshToken(email),
                    jwtTokenUtil.generateRefreshToken(email)));

        } catch ( MalformedJwtException | IllegalArgumentException | UnsupportedJwtException | SignatureException | ExpiredJwtException | BadCredentialsException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(INVALID_TOKEN.getCode(),
                    INVALID_TOKEN.getMessage()));
        }
    }

    @PostMapping(value = API +"/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> register(@RequestBody @Valid CreateAccountRequest request) {
        try {
            //notificationService.sendEmailConfirmation(request);
            userService.create(request);
            return ResponseEntity.ok().build();
//        } catch (MessagingException ex) {
//            LOGGER.warn(ex.getMessage());
//            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(SERVICE_UNAVAILABLE.getCode(),
//                    SERVICE_UNAVAILABLE.getMessage()));
//        }
        } catch (ValidationException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(CONFLICT.getCode(),
                    CONFLICT.getMessage()));
        } catch (AccountAlreadyExistException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(ACCOUNT_ALREADY_EXISTS.getCode(),
                    ACCOUNT_ALREADY_EXISTS.getMessage()));
        }
    }

    @GetMapping(value =API + "/resend", produces = "application/json")
    public ResponseEntity<Object> resendVerificationEmail(@RequestParam String email) {
        try {
            AccountDto accountDto = userService.getAccountDtoByEmail(email);
            notificationService.resendEmailConfirmation(accountDto);
            return ResponseEntity.ok().build();
        } catch (MessagingException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(SERVICE_UNAVAILABLE.getCode(),
                    SERVICE_UNAVAILABLE.getMessage()));
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch (AccountDisabledException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ACCOUNT_DISABLED.getCode(),
                    ACCOUNT_DISABLED.getMessage()));
        }
    }

    @GetMapping(value = API + "/verify", produces = "application/json")
    public ResponseEntity<Object> verifyEmail(@RequestParam String token, @RequestParam String email) {
        try {
            jwtTokenUtil.validateTokenFromEmail(token);
            userService.enableAccount(email);
            return ResponseEntity.ok()
                    .body("You have successfully verified your email address");
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch ( MalformedJwtException | IllegalArgumentException | UnsupportedJwtException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(INVALID_TOKEN.getCode(),
                    INVALID_TOKEN.getMessage()));
        } catch ( SignatureException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new ErrorResponse(INVALID_TOKEN_SIGNATURE.getCode(),
                    INVALID_TOKEN_SIGNATURE.getMessage()));
        } catch ( ExpiredJwtException ex) {
            LOGGER.warn(ex.getMessage());
            try{
                userService.isVerified(jwtTokenUtil.getUsername(token));
            } catch (AccountAlreadyVerifiedException e) {
                LOGGER.warn(ex.getMessage());
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(ACCOUNT_ALREADY_VERIFIED.getCode(),
                        ACCOUNT_ALREADY_VERIFIED.getMessage()));
            }
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse(EXPIRED_TOKEN.getCode(),
                    EXPIRED_TOKEN.getMessage()));
        }
    }

    @PostMapping(value = API + "/forgot", produces = "application/json", consumes = "application/json")
    public ResponseEntity<Object> forgotPassword(@RequestBody @Valid VerificationEmailDto emailDto) {
        try {
            notificationService.sendForgotEmailPassword(userService.getAccountDtoByEmail(emailDto.getEmail()));
            return ResponseEntity.ok().build();
        } catch (MessagingException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(SERVICE_UNAVAILABLE.getCode(),
                    SERVICE_UNAVAILABLE.getMessage()));
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch (AccountDisabledException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ACCOUNT_DISABLED.getCode(),
                    ACCOUNT_DISABLED.getMessage()));
        }
    }

    @GetMapping(value = API + "/recovery",consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> recoverPassword(@RequestBody @Valid PasswordResetRequest request){
        try{
            jwtTokenUtil.validateTokenFromEmail(request.getToken());
            String email = jwtTokenUtil.getUsername(request.getToken());
            userService.resetPassword(email, request.getPassword(), request.getMatchingPassword());
            notificationService.sendPasswordChangedConfirmationEmail(userService.getAccountDtoByEmail(email));
            return ResponseEntity.ok().build();

        } catch (ValidationException ex){
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(CONFLICT.getCode(),
                    CONFLICT.getMessage()));
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch (AccountDisabledException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ACCOUNT_DISABLED.getCode(),
                    ACCOUNT_DISABLED.getMessage()));
        } catch (MessagingException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(SERVICE_UNAVAILABLE.getCode(),
                    SERVICE_UNAVAILABLE.getMessage()));
        } catch ( MalformedJwtException | IllegalArgumentException | UnsupportedJwtException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(INVALID_TOKEN.getCode(),
                    INVALID_TOKEN.getMessage()));
        } catch ( SignatureException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new ErrorResponse(INVALID_TOKEN_SIGNATURE.getCode(),
                    INVALID_TOKEN_SIGNATURE.getMessage()));
        } catch ( ExpiredJwtException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse(EXPIRED_TOKEN.getCode(),
                    EXPIRED_TOKEN.getMessage()));
        }
    }

    @GetMapping(value = API + "/change", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> changePassword(@RequestBody @Valid ChangePasswordRequest request){
        try{
            jwtTokenUtil.validateTokenFromEmail(request.getToken());
            String email = jwtTokenUtil.getUsername(request.getToken());
            userService.changePassword(request, email);
            notificationService.sendPasswordChangedConfirmationEmail(userService.getAccountDtoByEmail(email));
            return ResponseEntity.ok().build();
        } catch (ValidationException ex){
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse(CONFLICT.getCode(),
                    CONFLICT.getMessage()));
        } catch (MessagingException ex){
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorResponse(CONFLICT.getCode(),
                    CONFLICT.getMessage()));
        } catch (AccountNotFoundException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(ACCOUNT_NOT_FOUND.getCode(),
                    ACCOUNT_NOT_FOUND.getMessage()));
        } catch (AccountDisabledException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(ACCOUNT_DISABLED.getCode(),
                    ACCOUNT_DISABLED.getMessage()));
        } catch (AuthenticationException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse(UNAUTHORIZED.getCode(),
                    UNAUTHORIZED.getMessage()));
        } catch ( MalformedJwtException | IllegalArgumentException | UnsupportedJwtException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse(INVALID_TOKEN.getCode(),
                    INVALID_TOKEN.getMessage()));
        } catch ( SignatureException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(new ErrorResponse(INVALID_TOKEN_SIGNATURE.getCode(),
                    INVALID_TOKEN_SIGNATURE.getMessage()));
        } catch ( ExpiredJwtException ex) {
            LOGGER.warn(ex.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ErrorResponse(EXPIRED_TOKEN.getCode(),
                    EXPIRED_TOKEN.getMessage()));
        }
    }
}
