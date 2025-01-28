package com.example.application.account.dao;

import com.example.application.account.Account;
import com.example.application.account.exception.AccountNotFoundException;
import com.example.application.account.exception.AccountDisabledException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Optional<Account> findById(Long id);
    Optional<Account> findByEmail(String email);
    Boolean existsAccountByEmail(String email);

    default Account getById(Long id) throws AccountNotFoundException, AccountDisabledException {
        Optional<Account> optionalUser = findById(id);
        if (optionalUser.isEmpty()) {
            throw new AccountNotFoundException();
        }
        if (Boolean.FALSE.equals(optionalUser.get().getProfile().getIsActive())) {
            throw new AccountDisabledException();
        }
        return optionalUser.get();
    }

    default Account getByEmail(String email) throws AccountNotFoundException, AccountDisabledException {
        Optional<Account> optionalUser = findByEmail(email);
        if (optionalUser.isEmpty()) {
            email = email.replace("@", "_%s@");
            optionalUser = findByEmail(email);
            if (optionalUser.isEmpty()) {
                throw new AccountNotFoundException();
            }else{
                throw new AccountDisabledException("Account disabled");
            }
        }
        return optionalUser.get();
    }

    default Account getAccountToEnable(String email) throws AccountNotFoundException {
        Optional<Account> optionalUser = findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new AccountNotFoundException();
        }
        return optionalUser.get();
    }

}
