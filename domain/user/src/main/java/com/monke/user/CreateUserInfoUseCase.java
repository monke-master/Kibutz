package com.monke.user;

import com.monke.identity.Constants;
import com.monke.identity.IdentityRepository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

public class CreateUserInfoUseCase {

    private final UserRepository userRepository;
    private final IdentityRepository identityRepository;

    @Inject
    public CreateUserInfoUseCase(UserRepository userRepository, IdentityRepository identityRepository) {
        this.userRepository = userRepository;
        this.identityRepository = identityRepository;
    }

    public void execute(String name, long dateOfBirth, boolean isMale) {
        User user = userRepository.getCurrentUser();
        user.setName(name);
        user.setDateOfBirth(dateOfBirth);
        Profile profile = new Profile(
                UUID.randomUUID().toString(),
                Collections.emptyList(),
                "",
                Collections.emptyList());
        if (isMale) {
            identityRepository
                    .getIdentityById(Constants.MALE_ID)
                    .ifPresent(identity -> profile.setIdentities(List.of(identity)));
        } else {
            identityRepository
                    .getIdentityById(Constants.FEMALE_ID)
                    .ifPresent(identity -> profile.setIdentities(List.of(identity)));
        }
        user.setProfile(profile);
        userRepository.saveUser(user);
    }
}
