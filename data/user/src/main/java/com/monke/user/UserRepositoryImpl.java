package com.monke.user;


import com.monke.di.AuthScope;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class UserRepositoryImpl implements UserRepository {

    private final UserCacheDataSource cacheDataSource;

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource) {
        this.cacheDataSource = cacheDataSource;
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
