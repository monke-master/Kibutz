package com.monke.user;

import androidx.lifecycle.LiveData;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

public interface UserRemoteDataSource {

    void createUser(UserRemote user, OnCompleteListener<Result<?>> listener);

    void getUserById(String id, OnCompleteListener<Result<UserRemote>> listener);
}
