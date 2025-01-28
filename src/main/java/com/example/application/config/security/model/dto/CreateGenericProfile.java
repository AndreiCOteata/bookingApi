package com.example.application.config.security.model.dto;

import com.example.application.account.profile.Profile;
import com.example.application.account.profile.Role;
import java.util.Collections;
import java.util.HashSet;

public class CreateGenericProfile {

    private CreateGenericProfile(){}

    public static Profile createProfile(Role role){
        Profile profile = new Profile();
        profile.setFailedLoginAttempts(0);
        profile.setLastFailedLoginTime(null);
        profile.setRoleList(new HashSet<>(Collections.singletonList(role)));
        return profile;
    }
}
