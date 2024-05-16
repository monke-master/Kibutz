package com.monke.user;

import javax.inject.Inject;

public class SavePasswordUseCase {

    private final UserRepository userRepository;

    @Inject
    public SavePasswordUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void execute(String password) {
        User user = userRepository.getCreatingUser();
        user.setPassword(password);
    }
}
