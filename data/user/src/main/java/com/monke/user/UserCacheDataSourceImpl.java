package com.monke.user;

import android.util.Log;


import com.monke.di.AuthScope;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserCacheDataSourceImpl implements UserCacheDataSource {

    private User user = Mocks.mockUser;

    @Inject
    public UserCacheDataSourceImpl() {
        Log.d("UserCacheDataSourceImpl", "constructor");
    }

    @Override
    public void saveUser(User user) {
        this.user = user;
    }

    @Override
    public User getUser() {
        return user;
    }
}
