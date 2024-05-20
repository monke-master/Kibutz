package com.monke.user;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

public interface UserRemoteDataSource {

    void setUser(UserRemote user, OnCompleteListener<Result<?>> listener);

    void getUserById(String id, OnCompleteListener<Result<UserRemote>> listener);
}
