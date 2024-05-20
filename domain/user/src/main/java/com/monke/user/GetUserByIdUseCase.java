package com.monke.user;

import java.util.Optional;

import javax.inject.Inject;

public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> execute(String id) {
        return userRepository.getLocalUserById(id);
    }
}
