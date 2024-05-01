package com.monke.user;

import com.monke.data.PasswordsDontMatchException;
import com.monke.data.Result;

import java.util.Objects;

import javax.inject.Inject;

public class VerifyPasswordUseCase {

    @Inject
    public VerifyPasswordUseCase() {

    }

    public Result<Boolean> execute(String password, String repeatedPassword) {
        if (Objects.equals(password, repeatedPassword)) {
            return new Result.Success<>(true);
        }
        return new Result.Failure<>(new PasswordsDontMatchException());
    }
}
