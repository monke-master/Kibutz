package com.monke.user;

import android.util.Log;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.di.AppScope;

import javax.inject.Inject;

@AppScope
public class UserCacheDataSourceImpl implements UserCacheDataSource {

    private MutableLiveData<User> currentUser = new MutableLiveData<>();
    private User creatingUser = null;

    @Inject
    public UserCacheDataSourceImpl() {
        Log.d("UserCacheDataSourceImpl", "constructor");
    }

    @Override
    public void saveCreatingUser(User user) {
        this.creatingUser = user;
    }

    @Override
    public User getCreatingUser() {
        return creatingUser;
    }

    @Override
    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    @Override
    public void saveCurrentUser(User user) {
        Log.d("UserCacheDataSourceImpl", user.toString());
        currentUser.setValue(user);
    }
}
