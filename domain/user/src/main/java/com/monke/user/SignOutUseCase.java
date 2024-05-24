package com.monke.user;

import javax.inject.Inject;

public class SignOutUseCase {

    private final UserRepository userRepository;

    @Inject
    public SignOutUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute() {
        userRepository.signOut();
    }
}
