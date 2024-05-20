package com.monke.user;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.FilesRepository;
import com.monke.data.Result;
import com.monke.di.AppScope;
import com.monke.identity.Identity;
import com.monke.identity.IdentityRepository;


import java.util.ArrayList;
import java.util.Optional;

import javax.inject.Inject;

@AppScope
public class UserRepositoryImpl implements UserRepository {

    private final UserCacheDataSource cacheDataSource;
    private final AuthDataSource authDataSource;
    private final UserRemoteDataSource remoteDataSource;
    private final FilesRepository filesRepository;
    private final IdentityRepository identityRepository;

    private ArrayList<User> users;
    private final String TAG = "UserRepositoryImpl";

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource,
                              AuthDataSource authDataSource,
                              UserRemoteDataSource remoteDataSource,
                              FilesRepository filesRepository,
                              IdentityRepository identityRepository) {
        Log.d("UserRepositoryImpl", "constructor");
        users = new ArrayList<>();
        users.add(Mocks.cockUser);
        users.add(Mocks.mockUser);
        this.cacheDataSource = cacheDataSource;
        this.authDataSource = authDataSource;
        this.remoteDataSource = remoteDataSource;
        this.filesRepository = filesRepository;
        this.identityRepository = identityRepository;
    }

    @Override
    public Optional<User> getUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public void setCreatingUser(User user) {
        cacheDataSource.saveCreatingUser(user);
    }

    @Override
    public User getCreatingUser() {
        return cacheDataSource.getCreatingUser();
    }

    @Override
    public void setCurrentUser(User user) {
        Log.d(TAG, user.toString());
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

            remoteDataSource.setUser(new UserRemote(user), creationRes -> {
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

    @Override
    public LiveData<Result<?>> signIn(String email, String password) {
        MutableLiveData<Result<?>> result = new MutableLiveData<>();
        authDataSource.signIn(email, password, signInResult -> {
            if (signInResult.isFailure()) {
                result.setValue(signInResult);
                return;
            }

            remoteDataSource.getUserById(signInResult.get(), userResult -> {
                if (userResult.isFailure()) {
                    result.setValue(userResult);
                    return;
                }

                var userRemote = userResult.get();

                ArrayList<Identity> identities = new ArrayList<>();
                for (String identityId: userRemote.profile.identitiesIds) {
                    identityRepository.getIdentityById(identityId).ifPresent(identities::add);
                }
                cacheDataSource.saveCurrentUser(userRemote.toDomain(identities));
                result.setValue(new Result.Success<>());
            });
        });
        return result;
    }

}
