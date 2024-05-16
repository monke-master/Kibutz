package com.monke.user;

import androidx.lifecycle.LiveData;

import javax.inject.Inject;

public class GetCurrentUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<User> execute() {
        return userRepository.getCurrentUser();
    }
}
