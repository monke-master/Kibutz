package com.monke.user;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.monke.di.AppScope;

import javax.inject.Inject;

@AppScope
public class UserRepositoryImpl implements UserRepository {

    private final UserCacheDataSource cacheDataSource;

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource) {
        this.cacheDataSource = cacheDataSource;
        Log.d("UserRepositoryImpl", "constructor");
    }

    @Override
    public User getUserById(String id) {
        return null;
    }

    @Override
    public void saveUser(User user) {
        cacheDataSource.saveCreatingUser(user);
    }

    @Override
    public User getCreatingUser() {
        return cacheDataSource.getCreatingUser();
    }

    @Override
    public void setCurrentUser(User user) {
        cacheDataSource.saveCurrentUser(user);
    }

    @Override
    public LiveData<User> getCurrentUser() {
        return cacheDataSource.getCurrentUser();
    }

    @Override
    public void signUp() {
        cacheDataSource.saveCurrentUser(cacheDataSource.getCreatingUser());
    }
}
