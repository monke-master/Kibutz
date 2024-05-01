package com.monke.user;

import com.monke.identity.Constants;
import com.monke.identity.IdentityRepository;

import java.util.List;

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
        if (isMale) {
            identityRepository
                    .getIdentityById(Constants.MALE_ID)
                    .ifPresent(identity -> user.getProfile().setIdentities(List.of(identity)));
        } else {
            identityRepository
                    .getIdentityById(Constants.FEMALE_ID)
                    .ifPresent(identity -> user.getProfile().setIdentities(List.of(identity)));
        }

        userRepository.saveUser(user);
    }
}
