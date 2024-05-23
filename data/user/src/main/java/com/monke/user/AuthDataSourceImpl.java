package com.monke.user;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.di.AppScope;
import com.monke.utils.ThreadUtils;

import java.util.UUID;

import javax.inject.Inject;

@AppScope
public class AuthDataSourceImpl implements AuthDataSource {

    private final FirebaseAuth auth;
    private MutableLiveData<Result<Boolean>> _emailConfirmed = new MutableLiveData<>(new Result.Success(false));
    private LiveData<Result<Boolean>> emailConfirmed = _emailConfirmed;
    private final String rawPassword;
    private final String TAG = "AuthDataSourceImpl";

    @Inject
    public AuthDataSourceImpl(FirebaseAuth auth) {
        this.auth = auth;
        rawPassword = UUID.randomUUID().toString();
    }

    @Override
    public void createUser(User user, OnCompleteListener<Result<String>> listener) {
        ThreadUtils.runOnBackground(() -> {
            auth.getCurrentUser().updatePassword(user.getPassword())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onComplete(new Result.Success(auth.getCurrentUser().getUid()));
                        } else {
                            listener.onComplete(new Result.Failure<>(task.getException()));
                        }
                    });
        });
    }

    @Override
    public LiveData<Result<Boolean>> sendConfirmationLetter(String email) {
        ThreadUtils.runOnBackground(() -> sendLetter(email));
        return emailConfirmed;
    }

    @Override
    public void signIn(String email, String password, OnCompleteListener<Result<String>> listener) {
        ThreadUtils.runOnBackground(() -> {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            listener.onComplete(new Result.Success<>(auth.getCurrentUser().getUid()));
                        } else {
                            listener.onComplete(new Result.Failure<>(task.getException()));
                        }
                    });
        });
    }

    private void sendLetter(String email) {
        auth.createUserWithEmailAndPassword(email, rawPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        ThreadUtils.runOnBackground(() -> {
                            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                                checkConfirmationStatus(email, rawPassword);
                            });
                        });
                    } else {
                        _emailConfirmed.setValue(new Result.Failure<>(task.getException()));
                    }
                });
    }

    private void checkConfirmationStatus(String email, String password) {
        ThreadUtils.runOnBackground(() -> {
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
                            Log.e(TAG, e.getMessage());
                        }
                    });
                }
            });
        });
    }
}
