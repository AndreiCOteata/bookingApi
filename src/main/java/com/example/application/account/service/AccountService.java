package com.example.application.account.service;

import com.example.application.account.Account;
import com.example.application.account.dao.AccountRepository;
import com.example.application.account.dao.dto.AccountConverterImpl;
import com.example.application.account.dao.dto.AccountDto;
import com.example.application.account.exception.*;
import com.example.application.account.profile.Profile;
import com.example.application.account.profile.ProfileService;
import com.example.application.common.date.CurrentTimeProvider;
import com.example.application.config.security.model.ChangePasswordRequest;
import com.example.application.config.security.model.CreateAccountRequest;
import com.example.application.config.security.model.UpdateAccountRequest;
import com.example.application.config.security.model.dto.UserToAccountConverterImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.naming.AuthenticationException;
import javax.security.auth.login.AccountLockedException;
import javax.validation.ValidationException;

@Service
@Slf4j
public class AccountService {

    private final UserToAccountConverterImpl converter;
    private final AccountRepository accountRepository;
    private final AccountConverterImpl accountConverter;
    private final PasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final CurrentTimeProvider currentTimeProvider = new CurrentTimeProvider();

    @Value("${app.jwtExpirationInMs}")
    private Long locked_time;

    @Autowired
    public AccountService(UserToAccountConverterImpl converter, AccountRepository accountRepository, AccountConverterImpl accountConverter, PasswordEncoder passwordEncoder, ProfileService profileService) {
        this.converter = converter;
        this.accountRepository = accountRepository;
        this.accountConverter = accountConverter;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
    }

    public AccountDto getAccountDtoById(Long id) throws AccountNotFoundException, AccountDisabledException {
        return accountConverter.createFrom(
                accountRepository.getById(id));
    }

    public AccountDto getAccountDtoByEmail(String email) throws AccountNotFoundException, AccountDisabledException {
        return accountConverter.createFrom(
                    accountRepository.getByEmail(email));
    }

    public void create(CreateAccountRequest request) throws AccountAlreadyExistException, ValidationException {
        if (this.emailExists(request.getEmail())) {
            throw new AccountAlreadyExistException();
        }
        if (!request.getPassword().equals(request.getMatchingPassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        System.out.println(passwordEncoder.encode("Andrei7314"));
        Account user = converter.createFrom(request);
        user.setProfile(profileService.createNew());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        accountRepository.save(user);
    }

    public void failedLogin(String email) throws AccountNotFoundException, AccountDisabledException {
        Account account = accountRepository.getByEmail(email);
        profileService.failedLogin(account.getId());
    }

    public boolean emailExists(String email) {
        return accountRepository.existsAccountByEmail(email);
    }

    public void allowLogin(String email) throws AccountDisabledException, AccountNotFoundException, AccountNotVerifiedException, AccountLockedException {
        Account account = accountRepository.getByEmail(email);
        Profile profile = account.getProfile();
        if(profile.getIsActive() == null){
            throw new AccountNotVerifiedException("Account hasn't been verified");
        }
        if(!profile.getIsActive()){
            long lastFailedLoginTime = profile.getLastFailedLoginTime().getTime();
            if(currentTimeProvider.getTimestamp().getTime() + locked_time <= lastFailedLoginTime){
                profile.setIsActive(true);
                profile.setFailedLoginAttempts(0);
                account.setProfile(profile);
                accountRepository.save(account);
            }else{
                long timeLeft = currentTimeProvider.getTimestamp().getTime() + locked_time - lastFailedLoginTime;
                throw new AccountLockedException(Long.toString(timeLeft));
            }
        }
    }

    public void resetPassword(String email, String newPassword, String matchingNewPassword) throws AccountNotFoundException, AccountDisabledException {
        Account account = accountRepository.getByEmail(email);
        if(newPassword.equals(matchingNewPassword)){
            account.setPassword(passwordEncoder.encode(newPassword));
            accountRepository.save(account);
        }else{
            throw new ValidationException("passwords don't matches");
        }
    }

    public void enableAccount(String email) throws AccountNotFoundException {
        Account account = accountRepository.getAccountToEnable(email);
        account.setProfile(profileService.enableProfile(account.getProfile()));
        accountRepository.save(account);
    }

    public void changePassword(ChangePasswordRequest request, String email) throws AuthenticationException, AccountNotFoundException, AccountDisabledException {
        Account account = accountRepository.getByEmail(email);
        if(passwordEncoder.matches(account.getPassword(), request.getCurrentPassword())){
            if(request.getPassword().equals(request.getMatchingPassword())){
                account.setPassword(passwordEncoder.encode(request.getPassword()));
                accountRepository.save(account);
            }else{
                throw new ValidationException("passwords don't matches");
            }
        } else {
            throw new AuthenticationException("Password is incorrect");
        }
    }

    public void isVerified(String email) throws  AccountAlreadyVerifiedException {
        try{
            if(accountRepository.getByEmail(email).getProfile().getIsActive()){
                throw new AccountAlreadyVerifiedException("Account has already been verified");
            }
        } catch (AccountDisabledException | AccountNotFoundException ex){
            LOGGER.warn(ex.getMessage());
        }
    }

    public Account update(Long id, UpdateAccountRequest request) throws AccountNotFoundException, AccountDisabledException {
        Account user = accountRepository.getById(id);
        converter.update(request, user);
        user = accountRepository.save(user);
        return user;
    }

    public String delete(Long id) throws AccountNotFoundException, AccountDisabledException {
        Account user = accountRepository.getById(id);
        user.setEmail(user.getEmail().replace("@", String.format("_%s@", user.getId().toString())));
        user.getProfile().setIsActive(false);
        accountRepository.save(user);

        return "Account disabled";
    }
}
