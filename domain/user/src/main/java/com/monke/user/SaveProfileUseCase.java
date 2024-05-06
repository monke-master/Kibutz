package com.monke.user;

import com.monke.identity.Identity;

import java.util.List;

import javax.inject.Inject;

public class SaveProfileUseCase {

    private UserRepository userRepository;

    @Inject
    public SaveProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String bio, List<Identity> identities) {
        User user = userRepository.getCurrentUser();
        Profile profile = user.getProfile();
        profile.setIdentities(identities);
        profile.setBio(bio);
        user.setProfile(profile);
        userRepository.saveUser(user);
    }
}
