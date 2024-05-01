package com.monke.user;

public interface UserRepository {

    User getUserById(String id);

    void saveUser(User user);

    User getCurrentUser();
}
