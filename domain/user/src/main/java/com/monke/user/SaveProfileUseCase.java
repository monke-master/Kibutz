package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;
import com.monke.identity.Identity;

import java.util.List;

import javax.inject.Inject;

public class SaveProfileUseCase {

    private UserRepository userRepository;

    @Inject
    public SaveProfileUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<?>> execute(String bio, List<Identity> identities, List<String> photos) {
        User user = userRepository.getCurrentUser().getValue().clone();
        Profile profile = user.getProfile();
        profile.setIdentities(identities);
        profile.setBio(bio);
        profile.setPhotosUrl(photos);
        user.setProfile(profile);
        return userRepository.setCurrentUser(user);
    }
}
