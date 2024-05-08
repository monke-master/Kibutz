package com.monke.user;

import android.util.Log;


import com.monke.di.AppScope;
import com.monke.di.AuthScope;

import javax.inject.Inject;
import javax.inject.Singleton;

@AppScope
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
