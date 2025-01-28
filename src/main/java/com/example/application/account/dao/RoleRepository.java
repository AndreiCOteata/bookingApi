package com.example.application.account.dao;

import com.example.application.account.profile.Role;
import com.example.application.account.profile.RoleEnum;
import com.example.application.common.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);

    default Role getByName(RoleEnum name) {
        Optional<Role> optionalRole = findByName(name);
        if (optionalRole.isEmpty()) {
            throw new ResourceNotFoundException("Role not found");
        }
        return optionalRole.get();
    }
}
