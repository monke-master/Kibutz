package com.monke.user;

import android.util.Log;

import com.monke.user.di.AuthScope;

import javax.inject.Inject;

@AuthScope
public class UserCacheDataSourceImpl implements UserCacheDataSource {

    private User user = null;

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
