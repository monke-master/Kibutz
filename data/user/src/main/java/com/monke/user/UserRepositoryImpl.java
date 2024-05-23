package com.monke.user;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.FilesRepository;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.di.AppScope;
import com.monke.identity.Identity;
import com.monke.identity.IdentityRepository;
import com.monke.rental.RentalRepository;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

@AppScope
public class UserRepositoryImpl implements UserRepository {

    private final UserCacheDataSource cacheDataSource;
    private final AuthDataSource authDataSource;
    private final UserRemoteDataSource remoteDataSource;
    private final FilesRepository filesRepository;
    private final IdentityRepository identityRepository;
    private final RentalRepository rentalRepository;
    private final UserPrefDataSource userPrefDataSource;
    private final String FILES_COLLECTION = "users";

    private ArrayList<User> users;
    private final String TAG = "UserRepositoryImpl";

    @Inject
    public UserRepositoryImpl(UserCacheDataSource cacheDataSource,
                              AuthDataSource authDataSource,
                              UserRemoteDataSource remoteDataSource,
                              FilesRepository filesRepository,
                              IdentityRepository identityRepository,
                              RentalRepository rentalRepository,
                              UserPrefDataSource userPrefDataSource) {
        Log.d("UserRepositoryImpl", "constructor");
        users = new ArrayList<>();
        users.add(Mocks.cockUser);
        users.add(Mocks.mockUser);
        this.cacheDataSource = cacheDataSource;
        this.authDataSource = authDataSource;
        this.remoteDataSource = remoteDataSource;
        this.filesRepository = filesRepository;
        this.identityRepository = identityRepository;
        this.rentalRepository = rentalRepository;
        this.userPrefDataSource = userPrefDataSource;
    }

    @Override
    public Optional<User> getLocalUserById(String id) {
        return users.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    @Override
    public LiveData<Result<User>> getUserById(String id) {
        MutableLiveData<Result<User>> result = new MutableLiveData<>();
        getUserById(id, userRes -> {
            if (userRes.isFailure()) {
                result.setValue(new Result.Failure<>(userRes.getException()));
                return;
            }
            result.setValue(new Result.Success<>(userRes.get().toDomain(Collections.emptyList(), Collections.emptyList())));
        });
        return result;
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
    public LiveData<Result<?>> setCurrentUser(User user) {
        var oldUser = cacheDataSource.getCurrentUser().getValue();
        Log.d(TAG, user.toString());
        MutableLiveData<Result<?>> result = new MutableLiveData<>();
        List<String> photoToUpload = getPhotoToUpload(oldUser, user);
        filesRepository.uploadImages(FILES_COLLECTION, photoToUpload, uploadRes -> {
            if (uploadRes.isFailure()) {
                result.setValue(uploadRes);
                return;
            }

            var path = photoToUpload
                        .stream()
                        .map(url -> FILES_COLLECTION + "/" + url)
                        .collect(Collectors.toList());
            // Get new photos urls
            filesRepository.getFilesDownloadUrls(path, filesResult -> {
                // On error (again?)
                if (filesResult.isFailure()) {
                    result.setValue(filesResult);
                    return;
                }

                var newPhotos = new ArrayList<>(oldUser.getProfile().getPhotosUrl());
                newPhotos.addAll(filesResult.get());
                user.getProfile().setPhotosUrl(newPhotos);

                remoteDataSource.setUser(new UserRemote(user), userResult -> {
                    if (userResult.isFailure()) {
                        result.setValue(userResult);
                        return;
                    }

                    cacheDataSource.saveCurrentUser(user);
                    result.setValue(new Result.Success<>());
                });
            });
        });
        return result;
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
                userPrefDataSource.saveUserCredentials(user.getEmail(), user.getPassword());
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
        // Try to sign in
        authDataSource.signIn(email, password, signInResult -> {
            // On Error
            if (signInResult.isFailure()) {
                result.setValue(signInResult);
                return;
            }

            // Fetching user data
            getUserById(signInResult.get(), userResult -> {
                if (userResult.isFailure()) {
                    result.setValue(userResult);
                    return;
                }

                var userRemote = userResult.get();
                // Get rentals (oh my god)
                rentalRepository.getUserRentals(userRemote.rentalsIds).observeForever(rentalsRes -> {
                    if (rentalsRes.isFailure()) {
                        result.setValue(rentalsRes);
                        return;
                    }

                    // Get responses
                    rentalRepository.getResponses(userRemote.responsesIds, responsesResult -> {
                        if (responsesResult.isFailure()) {
                            result.setValue(responsesResult);
                            return;
                        }

                        // Save data to cache
                        cacheDataSource.saveCurrentUser(userRemote.toDomain(
                                rentalsRes.get(), responsesResult.get()));
                        userPrefDataSource.saveUserCredentials(email, password);
                        result.setValue(new Result.Success<>());
                    });
                });
            });
        });
        return result;
    }


    private List<String> getPhotoToUpload(User oldUser, User newUser) {
        if (oldUser == null) {
            return newUser.getProfile().getPhotosUrl();
        }
        return newUser.getProfile()
                .getPhotosUrl()
                .stream()
                .filter(url -> !oldUser.getProfile().getPhotosUrl().contains(url))
                .collect(Collectors.toList());
    }

    private void getUserById(String userId, OnCompleteListener<Result<UserRemote>> listener) {
        remoteDataSource.getUserById(userId, userResult -> {
            // On error
            if (userResult.isFailure()) {
                listener.onComplete(new Result.Failure<>(userResult.getException()));
                return;
            }

            var userRemote = userResult.get();

            // Get identities data
            ArrayList<Identity> identities = new ArrayList<>();
            for (String identityId: userRemote.profile.identitiesIds) {
                identityRepository.getIdentityById(identityId).ifPresent(identities::add);
            }
            userRemote.setIdentities(identities);
            listener.onComplete(new Result.Success<>(userRemote));
        });
    }

    @Override
    public void updateLocalUserData(User user) {
        cacheDataSource.saveCurrentUser(user);
    }

    @Override
    public boolean hasAuthenticatedUser() {
        return userPrefDataSource.getUserCredentials() != null;
    }

    @Override
    public LiveData<Result<?>> signInWithSavedCredentials() {
        var credentials = userPrefDataSource.getUserCredentials();
        return signIn(credentials.first, credentials.second);
    }
}
