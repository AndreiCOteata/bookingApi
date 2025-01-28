package com.example.application.config.security;

import com.example.application.account.dao.dto.AccountDto;
import com.example.application.account.exception.AccountNotFoundException;
import com.example.application.account.exception.AccountDisabledException;
import com.example.application.account.service.AccountService;
import com.example.application.config.security.model.AccountSecurity;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    public CustomUserDetailsService() {
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userId) {
        AccountDto moderator = null;
        try {
            moderator = accountService.getAccountDtoByEmail(userId);
        } catch (AccountNotFoundException | AccountDisabledException e) {
            LOGGER.warn(e.getMessage());
        }
        assert moderator != null;
        return AccountSecurity.create(moderator);
    }
}
