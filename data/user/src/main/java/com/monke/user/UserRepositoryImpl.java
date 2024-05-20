package com.monke.user;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.FilesRepository;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.di.AppScope;


import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

@AppScope
public class UserRepositoryImpl implements UserRepository {

    private final UserCacheDataSource cacheDataSource;
    private final AuthDataSource authDataSource;
    private final UserRemoteDataSource remoteDataSource;
    private final FilesRepository filesRepository;

    private ArrayList<User> users;

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource,
                              AuthDataSource authDataSource,
                              UserRemoteDataSource remoteDataSource,
                              FilesRepository filesRepository) {
        Log.d("UserRepositoryImpl", "constructor");
        this.cacheDataSource = cacheDataSource;
        this.authDataSource = authDataSource;
        this.remoteDataSource = remoteDataSource;
        this.filesRepository = filesRepository;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public void saveUser(User user) {
        cacheDataSource.saveCreatingUser(user);
    }

    @Override
    public User getCreatingUser() {
        return cacheDataSource.getCreatingUser();
    }

    @Override
    public void setCurrentUser(User user) {
        cacheDataSource.saveCurrentUser(user);
    }

    @Override
    public LiveData<User> getCurrentUser() {
        return cacheDataSource.getCurrentUser();
    }

    @Override
    public LiveData<Result<?>> signUp() {
        MutableLiveData<Result<?>> res = new MutableLiveData<>();
        User user = cacheDataSource.getCreatingUser();
        authDataSource.createUser(user, result -> {
            if (result.isFailure()) {
                res.setValue(result);
                return;
            }
            String id = result.get();
            user.setId(id);

            remoteDataSource.createUser(new UserRemote(user), creationRes -> {
                if (creationRes.isFailure()) {
                    res.setValue(creationRes);
                    return;
                }
                cacheDataSource.saveCurrentUser(user);
                res.setValue(new Result.Success<>());
            });
        });

        return res;
    }

    @Override
    public LiveData<Result<Boolean>> sendConfirmationLetter(String email) {
        return authDataSource.sendConfirmationLetter(email);
    }
}
