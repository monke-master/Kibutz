package com.monke.user;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
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
    public void setUser(UserRemote user, OnCompleteListener<Result<?>> completeListener) {
        firestore
                .collection(USERS_COLLECTION)
                .document(user.id)
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
    public void getUserById(String id, OnCompleteListener<Result<UserRemote>> onCompleteListener) {
        firestore
                .collection(USERS_COLLECTION)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        var user = task.getResult().toObject(UserRemote.class);
                        onCompleteListener.onComplete(new Result.Success<>(user));
                    } else {
                        onCompleteListener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }
}
