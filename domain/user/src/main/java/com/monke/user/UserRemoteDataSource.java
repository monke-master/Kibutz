package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

public interface UserRemoteDataSource {

    LiveData<Result<String>> createUser(User user);

    LiveData<Result<Boolean>> sendConfirmationLetter(String email);

}
