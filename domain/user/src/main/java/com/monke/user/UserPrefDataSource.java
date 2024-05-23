package com.monke.user;

import android.util.Pair;

public interface UserPrefDataSource {

    void saveUserCredentials(String email, String password);

    Pair<String, String> getUserCredentials();
}
