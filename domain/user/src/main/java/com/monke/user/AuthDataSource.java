package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

public interface AuthDataSource {

    void createUser(User user, OnCompleteListener<Result<String>> onCompleteListener);

    LiveData<Result<Boolean>> sendConfirmationLetter(String email);

    void signIn(String email, String password, OnCompleteListener<Result<String>> listener);

    void signOut();
}
