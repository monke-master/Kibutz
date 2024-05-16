package com.monke.user;

import androidx.lifecycle.LiveData;

public interface UserRepository {

    User getUserById(String id);

    void saveUser(User user);

    User getCreatingUser();

    LiveData<User> getCurrentUser();

    void setCurrentUser(User user);

    void signUp();
}
