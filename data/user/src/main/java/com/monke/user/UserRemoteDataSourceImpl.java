package com.monke.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

import javax.inject.Inject;

public class UserRemoteDataSourceImpl implements UserRemoteDataSource {

    private final String TAG = "UserRemoteDataSourceImpl";
    private final FirebaseFirestore firestore;

    private final String USERS_COLLECTION = "users";

    @Inject
    public UserRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
        Log.d(TAG, "constructor, bitch");
    }

    @Override
    public void createUser(UserRemote user, OnCompleteListener<Result<?>> completeListener) {
        firestore
                .collection(USERS_COLLECTION)
                .document(user.getId())
                .set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        completeListener.onComplete(new Result.Success<>());
                    } else {
                        completeListener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }

    @Override
    public LiveData<Result<User>> getUserById(String id) {
        return null;
    }
}
