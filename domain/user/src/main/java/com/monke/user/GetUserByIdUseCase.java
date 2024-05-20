package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;

import java.util.Optional;

import javax.inject.Inject;

public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<User>> execute(String id) {
        return userRepository.getUserById(id);
    }
}
