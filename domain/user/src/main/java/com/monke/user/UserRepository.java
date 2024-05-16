package com.monke.user;

import androidx.lifecycle.LiveData;

import java.util.Optional;

public interface UserRepository {

    Optional<User> getUserById(String id);

    void saveUser(User user);

    User getCreatingUser();

    LiveData<User> getCurrentUser();

    void setCurrentUser(User user);

    void signUp();
}
