package com.monke.user;

import javax.inject.Inject;

public class UserRepositoryImpl implements UserRepository {

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource) {

    }

    @Override
    public User getUserById(String id) {
        return null;
    }
}
