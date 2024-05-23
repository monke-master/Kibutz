package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getLocalUserById(String id);

    LiveData<Result<User>> getUserById(String id);

    void setCreatingUser(User user);

    User getCreatingUser();

    LiveData<User> getCurrentUser();

    LiveData<Result<?>> setCurrentUser(User user);

    LiveData<Result<Boolean>> sendConfirmationLetter(String email);

    LiveData<Result<?>> signUp();

    LiveData<Result<?>> signIn(String email, String password);

    void updateLocalUserData(User user);

    boolean hasAuthenticatedUser();

    LiveData<Result<?>> signInWithSavedCredentials();

}
