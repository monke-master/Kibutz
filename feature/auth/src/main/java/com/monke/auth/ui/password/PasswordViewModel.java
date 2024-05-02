package com.monke.auth.ui.password;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.data.Result;
import com.monke.data.UiStatusState;
import com.monke.user.SavePasswordUseCase;
import com.monke.user.VerifyPasswordUseCase;

import javax.inject.Inject;

public class PasswordViewModel extends ViewModel {

    private final SavePasswordUseCase savePasswordUseCase;
    private final VerifyPasswordUseCase verifyPasswordUseCase;

    private PasswordUiState passwordUiState = new PasswordUiState();

    private MutableLiveData<UiStatusState> _uiStatusState =
            new MutableLiveData<>(new UiStatusState.Default());
    private LiveData<UiStatusState> uiStatus = _uiStatusState;

    public PasswordViewModel(SavePasswordUseCase savePasswordUseCase,
                             VerifyPasswordUseCase verifyPasswordUseCase) {
        this.savePasswordUseCase = savePasswordUseCase;
        this.verifyPasswordUseCase = verifyPasswordUseCase;
    }

    public void savePassword() {
        Result<Boolean> result = verifyPasswordUseCase.execute(
                passwordUiState.getPassword(),
                passwordUiState.getRepeatedPassword());

        if (result instanceof Result.Success<Boolean>) {
            savePasswordUseCase.execute(passwordUiState.getPassword());

            _uiStatusState.setValue(new UiStatusState.Success());
        } else {
            var exception = ((Result.Failure<?>)result).getException();
            _uiStatusState.setValue(new UiStatusState.Error(exception));
        }
    }

    public PasswordUiState getUiState() {
        return passwordUiState;
    }

    public LiveData<UiStatusState> getUiStatus() {
        return uiStatus;
    }

    public void clearStatus() {
        _uiStatusState.setValue(new UiStatusState.Default());
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final SavePasswordUseCase savePasswordUseCase;
        private final VerifyPasswordUseCase verifyPasswordUseCase;

        @Inject
        public Factory(SavePasswordUseCase savePasswordUseCase,
                       VerifyPasswordUseCase verifyPasswordUseCase) {
            this.savePasswordUseCase = savePasswordUseCase;
            this.verifyPasswordUseCase = verifyPasswordUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new PasswordViewModel(savePasswordUseCase, verifyPasswordUseCase));
        }
    }
}