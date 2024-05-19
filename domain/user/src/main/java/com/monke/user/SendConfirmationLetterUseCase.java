package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import javax.inject.Inject;

public class SendConfirmationLetterUseCase {

    private final UserRepository userRepository;

    @Inject
    public SendConfirmationLetterUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<Boolean>> execute(String email) {
        return userRepository.sendConfirmationLetter(email);
    }
}
