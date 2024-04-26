package com.monke.user;

import javax.inject.Inject;

public class UserCacheDataSourceImpl implements UserCacheDataSource {

    private User user = null;

    @Inject
    public UserCacheDataSourceImpl() {

    }

    @Override
    public void saveUser(User user) {
        this.user = user;
    }
}
