package com.example.application.account.profile;

import com.example.application.account.dao.ProfileRepository;
import com.example.application.common.date.CurrentTimeProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.Set;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final RoleService roleService;
    private final CurrentTimeProvider currentTimeProvider = new CurrentTimeProvider();

    @Autowired
    public ProfileService(ProfileRepository profileRepository, RoleService roleService) {
        this.profileRepository = profileRepository;
        this.roleService = roleService;
    }

    public Profile createNew() {
        Profile profile = new Profile();
        profile.setLastFailedLoginTime(null);
        profile.setFailedLoginAttempts(0);
        profile.setIsActive(null);
        profile.setLanguage("en");
        Set<Role> roleSet = new HashSet<>();
        roleSet.add(roleService.getUserRole(RoleEnum.ROLE_USER));
        profile.setRoleList(roleSet);
        return profile;
    }

    public Profile enableProfile(Profile profile){
        profile.setIsActive(true);
        profile.setFailedLoginAttempts(0);
        profile.setLastFailedLoginTime(null);
        return profile;
    }

    public void failedLogin(Long accountId){
        Profile profile = profileRepository.getProfileByAccountId(accountId);
        int failedAttempts = profile.getFailedLoginAttempts()+1;
        if(failedAttempts == 3) {
            profile.setIsActive(false);
        }
        profile.setFailedLoginAttempts(failedAttempts);
        profile.setLastFailedLoginTime(currentTimeProvider.getTimestamp());
        profileRepository.save(profile);
    }

    public Profile addAdminRole(Long accountId){
        Profile profile = profileRepository.getProfileByAccountId(accountId);
        Set<Role> roleSet = profile.getRoleList();
        roleSet.add(roleService.getUserRole(RoleEnum.ROLE_ADMIN));
        profile.setRoleList(roleSet);
        return profile;
    }
}
