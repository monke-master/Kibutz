package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;

import javax.inject.Inject;

public class TryToSignInUseCase {

    private final UserRepository userRepository;

    @Inject
    public TryToSignInUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<Boolean>> execute() {
        var result = new MutableLiveData<Result<Boolean>>();

        if (!userRepository.hasAuthenticatedUser()) {
            result.setValue(new Result.Success<>(false));
            return result;
        }

        userRepository.signInWithSavedCredentials().observeForever(res -> {
            if (res.isFailure()) {
                result.setValue(new Result.Failure<>(res.getException()));
                return;
            }

            result.setValue(new Result.Success<>(true));
        });

        return result;
    }
}
