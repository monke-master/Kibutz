package com.monke.user;


import android.util.Log;

import androidx.lifecycle.LiveData;

import com.monke.di.AppScope;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

@AppScope
public class UserRepositoryImpl implements UserRepository {

    private final UserCacheDataSource cacheDataSource;

    private ArrayList<User> users;

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource) {
        this.cacheDataSource = cacheDataSource;
        Log.d("UserRepositoryImpl", "constructor");
        users = new ArrayList<>(List.of(Mocks.mockUser, Mocks.cockUser));
    }

    @Override
    public Optional<User> getUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
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
