package com.monke.user;

import javax.inject.Inject;

public class SaveEmailUseCase {

    private final UserRepository userRepository;

    @Inject
    public SaveEmailUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String email) {
        User user = new User(email);
        userRepository.setCreatingUser(user);
    }
}
