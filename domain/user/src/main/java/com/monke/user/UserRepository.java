package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserById(String id);

    void saveUser(User user);

    User getCreatingUser();

    LiveData<User> getCurrentUser();

    void setCurrentUser(User user);

    LiveData<Result<Boolean>> sendConfirmationLetter(String email);

    LiveData<Result<?>> signUp();

    LiveData<Result<?>> signIn(String email, String password);

}
