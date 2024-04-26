package com.monke.user;

import javax.inject.Inject;

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
}
