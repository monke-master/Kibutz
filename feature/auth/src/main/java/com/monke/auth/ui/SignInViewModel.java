package com.monke.auth.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.auth.SignInUiState;
import com.monke.auth.ui.email.EmailViewModel;
import com.monke.data.UiStatusState;
import com.monke.user.SaveEmailUseCase;
import com.monke.user.SendConfirmationLetterUseCase;
import com.monke.user.SignInUseCase;

import javax.inject.Inject;

public class SignInViewModel extends ViewModel {

    private final SignInUseCase signInUseCase;

    private final MutableLiveData<UiStatusState> _uiStatusState =
            new MutableLiveData<>(new UiStatusState.Default());
    private final LiveData<UiStatusState> uiStatusState = _uiStatusState;

    public SignInUiState uiState = new SignInUiState();

    public SignInViewModel(SignInUseCase signInUseCase) {
        this.signInUseCase = signInUseCase;
    }

    public void signIn() {
        signInUseCase.execute(uiState.getEmail(), uiState.getPassword()).observeForever(result -> {
            _uiStatusState.setValue(UiStatusState.fromResult(result));
        });
    }

    public LiveData<UiStatusState> getUiStatusState() {
        return uiStatusState;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final SignInUseCase signInUseCase;

        @Inject
        public Factory(SignInUseCase signInUseCase) {
            this.signInUseCase = signInUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new SignInViewModel(signInUseCase));
        }
    }
}