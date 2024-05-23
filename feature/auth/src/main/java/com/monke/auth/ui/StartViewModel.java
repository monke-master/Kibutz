package com.monke.auth.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.auth.di.AuthComponentProvider;
import com.monke.user.TryToSignInUseCase;

import javax.inject.Inject;

public class StartViewModel extends ViewModel {

    private TryToSignInUseCase tryToSignInUseCase;

    private MutableLiveData<Boolean> _authenticated = new MutableLiveData<>();
    public LiveData<Boolean> authenticated = _authenticated;


    public StartViewModel(TryToSignInUseCase tryToSignInUseCase) {
        this.tryToSignInUseCase = tryToSignInUseCase;
    }

    public void init() {
        trySignIn();
    }

    private void trySignIn() {
        tryToSignInUseCase.execute().observeForever(result -> {
            if (result.isFailure()) {
                return;
            }
            _authenticated.setValue(result.get());
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public static class Factory implements ViewModelProvider.Factory {

        private TryToSignInUseCase tryToSignInUseCase;

        @Inject
        public Factory(TryToSignInUseCase tryToSignInUseCase) {
            this.tryToSignInUseCase = tryToSignInUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new StartViewModel(tryToSignInUseCase));
        }
    }
}