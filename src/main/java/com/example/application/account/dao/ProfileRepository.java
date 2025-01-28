package com.example.application.account.dao;

import com.example.application.account.profile.Profile;
import com.example.application.common.exceptions.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> getByAccountId(Long accountId);

    default Profile getProfileByAccountId(Long accountId) {
        Optional<Profile> optionalProfile = getByAccountId(accountId);
        if (optionalProfile.isEmpty()) {
            throw new ResourceNotFoundException("Role not found");
        }
        return optionalProfile.get();
    }

}
