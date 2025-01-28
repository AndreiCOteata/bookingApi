package com.example.application.config.security.model.dto;

import com.example.application.account.Account;
import com.example.application.account.dao.RoleRepository;
import com.example.application.account.profile.RoleEnum;
import com.example.application.config.security.model.CreateAccountRequest;
import com.example.application.config.security.model.UpdateAccountRequest;
import com.example.application.location.address.dao.dto.AddressConverterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserToAccountConverterImpl {

    private final AddressConverterImpl addressConverter;
    private final RoleRepository roleRepository;

    @Autowired
    public UserToAccountConverterImpl(AddressConverterImpl addressConverter, RoleRepository roleRepository) {
        this.addressConverter = addressConverter;
        this.roleRepository = roleRepository;
    }

    public Account createFrom(CreateAccountRequest dto) {
        Account account = new Account();
        if(dto != null) {
            account.setEmail(dto.getEmail());
            account.setFirstName(dto.getFirstName());
            account.setLastName(dto.getLastName());
            account.setPassword(dto.getPassword());
            account.setProfile(CreateGenericProfile
                    .createProfile(this.roleRepository.getByName(RoleEnum.ROLE_USER)));
            return account;
        }
        return null;
    }

    public Account update(UpdateAccountRequest dto, Account account){
        if(dto != null && account != null) {
            if(account.getAddress() != null){
                account.setAddress(addressConverter.createFrom(dto.getAddressDto()));
            }
            account.setFirstName(dto.getFirstName());
            account.setLastName(dto.getLastName());
            return account;
        }
        return null;
    }
}
