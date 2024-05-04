package com.monke.user;

import javax.inject.Inject;

public class GetCurrentUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute() {
        return userRepository.getCurrentUser();
    }
}
