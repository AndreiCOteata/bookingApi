package com.example.application.account.dao.dto;

import com.example.application.account.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountConverterImpl implements AccountConverter{

    @Override
    public Account createFrom(AccountDto dto) {
        Account account = new Account();
        if(dto != null) {
            dto.setEmail(account.getEmail());
            dto.setFirstName(account.getFirstName());
            dto.setLastName(account.getLastName());
            dto.setPassword(account.getPassword());
        }
        return null;
    }

    @Override
    public AccountDto createFrom(Account entity) {
        AccountDto accountDto = new AccountDto();
        if(entity != null){
            accountDto.setEmail(entity.getEmail());
            accountDto.setFirstName(entity.getFirstName());
            accountDto.setLastName(entity.getLastName());
            accountDto.setPassword(entity.getPassword());
            accountDto.setRoles(entity.getProfile().getRoleList());
        }
        return accountDto;
    }

    @Override
    public Account updateEntity(Account entity, AccountDto dto) {
        return null;
    }
}
