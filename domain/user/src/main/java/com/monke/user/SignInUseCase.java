package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import javax.inject.Inject;

public class SignInUseCase {

    private UserRepository userRepository;

    @Inject
    public SignInUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<?>> execute(String email, String password) {
        return userRepository.signIn(email, password);
    }
}
