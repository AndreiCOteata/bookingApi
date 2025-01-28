package com.example.application.account.dao.dto;

import com.example.application.account.profile.Role;
import com.example.application.common.model.AbstractDto;
import com.example.application.account.dao.dto.validator.EmailConstraint;
import com.example.application.account.dao.dto.validator.ValidPassword;
import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
public class AccountDto extends AbstractDto {

    private static final long serialVersionUID = -6398715484127355003L;

    private Long id;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.firstName}")
    private String firstName;

    @NotNull
    @Size(min = 1, message = "{Size.userDto.lastName}")
    private String lastName;

    @EmailConstraint
    @NotNull
    @Size(min = 1, message = "{Size.userDto.email}")
    private String email;

    @ValidPassword
    private String password;

    private Set<Role> roles = new HashSet<>();
}
