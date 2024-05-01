package com.monke.user;

public interface UserCacheDataSource {

    void saveUser(User user);

    User getUser();
}
