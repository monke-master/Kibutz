package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import javax.inject.Inject;

public class SignUpUseCase {

    private final UserRepository userRepository;

    @Inject
    public SignUpUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<?>> execute() {
        return userRepository.signUp();
    }
}
