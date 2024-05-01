package com.monke.user;

import com.monke.user.di.AuthScope;

import javax.inject.Inject;

@AuthScope
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
