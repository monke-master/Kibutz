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

    private MutableLiveData<PasswordUiState> _passwordUiState =
            new MutableLiveData<>(new PasswordUiState());
    private LiveData<PasswordUiState> uiState = _passwordUiState;

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
                _passwordUiState.getValue().getPassword(),
                _passwordUiState.getValue().getRepeatedPassword());

        if (result instanceof Result.Success<Boolean>) {
            savePasswordUseCase.execute(_passwordUiState.getValue().getPassword());

            PasswordUiState state = _passwordUiState.getValue();
            _uiStatusState.setValue(new UiStatusState.Success());
            _passwordUiState.setValue(state);
        } else {
            PasswordUiState state = _passwordUiState.getValue();
            var exception = ((Result.Failure<?>)result).getException();
            _uiStatusState.setValue(new UiStatusState.Error(exception));
            _passwordUiState.setValue(state);
        }
    }

    public LiveData<PasswordUiState> getUiState() {
        return uiState;
    }

    public LiveData<UiStatusState> getUiStatus() {
        return uiStatus;
    }

    public void setPassword(String password) {
        PasswordUiState state = _passwordUiState.getValue();
        state.setPassword(password);
        _passwordUiState.setValue(state);
    }

    public void setRepeatedPassword(String repeatedPassword) {
        PasswordUiState state = _passwordUiState.getValue();
        state.setRepeatedPassword(repeatedPassword);
        _passwordUiState.setValue(state);
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