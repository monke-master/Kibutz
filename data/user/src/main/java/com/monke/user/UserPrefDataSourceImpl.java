package com.monke.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Pair;

import javax.inject.Inject;

public class UserPrefDataSourceImpl implements UserPrefDataSource {

    private final String PASSWORD_KEY = "password";
    private final String EMAIL_KEY = "email";
    private final String USER_PREF = "userPref";
    private SharedPreferences sharedPreferences;

    @Inject
    public UserPrefDataSourceImpl(Context context) {
        sharedPreferences = context.getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public void saveUserCredentials(String email, String password) {
        sharedPreferences
                .edit()
                .putString(EMAIL_KEY, email)
                .putString(PASSWORD_KEY, password)
                .apply();
    }

    @Override
    public Pair<String, String> getUserCredentials() {
        String email = sharedPreferences.getString(EMAIL_KEY, null);
        if (email == null) return null;
        return new Pair<>(email, sharedPreferences.getString(PASSWORD_KEY, null));
    }
}
