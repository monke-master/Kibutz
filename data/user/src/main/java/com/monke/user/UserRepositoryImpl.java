package com.monke.user;


import android.util.Log;

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
        cacheDataSource.saveUser(user);
    }

    @Override
    public User getCurrentUser() {
        return cacheDataSource.getUser();
    }
}
