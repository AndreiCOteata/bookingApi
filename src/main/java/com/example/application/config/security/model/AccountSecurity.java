package com.example.application.config.security.model;

import com.example.application.account.dao.dto.AccountDto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class AccountSecurity implements UserDetails {

    private static final long serialVersionUID = 1L;

    private final Long id;

    private final String emailId;

    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public AccountSecurity(Long id, String emailId, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
        this.authorities = authorities;
    }

    public static AccountSecurity create(AccountDto user) {
        Set<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
        return new AccountSecurity(user.getId(), user.getEmail(),
                user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.emailId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AccountSecurity that = (AccountSecurity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
