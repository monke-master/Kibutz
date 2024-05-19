package com.monke.user;

import android.util.Log;

import androidx.annotation.WorkerThread;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.monke.data.Result;
import com.monke.di.AppScope;
import com.monke.utils.ThreadUtils;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

@AppScope
public class UserRemoteDataSourceImpl implements UserRemoteDataSource {

    private final FirebaseAuth auth;
    private MutableLiveData<Result<Boolean>> _emailConfirmed = new MutableLiveData<>(new Result.Success(false));
    private LiveData<Result<Boolean>> emailConfirmed = _emailConfirmed;
    private final String rawPassword;

    @Inject
    public UserRemoteDataSourceImpl(FirebaseAuth auth) {
        this.auth = auth;
        rawPassword = UUID.randomUUID().toString();
    }


    @Override
    public LiveData<Result<String>> createUser(User user) {
        MutableLiveData<Result<String>> result = new MutableLiveData<>();
        auth.getCurrentUser().updatePassword(user.getPassword())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        result.setValue(new Result.Success<>(auth.getCurrentUser().getUid()));
                    } else {
                        result.setValue(new Result.Failure<>(task.getException()));
                    }
                });
        return result;
    }

    @Override
    public LiveData<Result<Boolean>> sendConfirmationLetter(String email) {
        ThreadUtils.runOnBackground(() -> sendLetter(email));
        return emailConfirmed;
    }

    private void sendLetter(String email) {
        auth.createUserWithEmailAndPassword(email, rawPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ThreadUtils.runOnBackground(() -> {
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                ThreadUtils.runOnBackground(() -> checkConfirmationStatus(email, rawPassword));
                            });
                        });
                    } else {
                        _emailConfirmed.setValue(new Result.Failure<>(task.getException()));
                    }
                });
    }

    private void checkConfirmationStatus(String email, String password) {
        Log.d("awaitForEmailConfirmation", Thread.currentThread().getName());
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                _emailConfirmed.setValue(new Result.Failure<>(task.getException()));
            }
            if (auth.getCurrentUser().isEmailVerified()) {
                _emailConfirmed.setValue(new Result.Success<>(true));
            } else {
                ThreadUtils.runOnBackground(() -> {
                    try {
                        Thread.sleep(5000L);
                        checkConfirmationStatus(email, password);
                    } catch (InterruptedException e) {

                    }
                });
            }
        });
    }
}
