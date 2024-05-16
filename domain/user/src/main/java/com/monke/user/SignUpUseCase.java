package com.monke.user;

import javax.inject.Inject;

public class SignUpUseCase {

    private final UserRepository userRepository;

    @Inject
    public SignUpUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() {
        userRepository.signUp();
    }
}
