package com.monke.user;

import androidx.lifecycle.LiveData;

public interface UserCacheDataSource {

    void saveCreatingUser(User user);

    User getCreatingUser();

    void saveCurrentUser(User user);

    LiveData<User> getCurrentUser();
}
