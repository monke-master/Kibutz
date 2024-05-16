package com.monke.auth.ui.info;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.data.UiStatusState;
import com.monke.user.CreateUserInfoUseCase;
import com.monke.user.SignUpUseCase;

import javax.inject.Inject;

public class UserInfoViewModel extends ViewModel {

    private final CreateUserInfoUseCase createUserInfoUseCase;
    private final SignUpUseCase signUpUseCase;

    private final MutableLiveData<UiStatusState> _uiStatusState =
            new MutableLiveData<>(new UiStatusState.Default());
    private final LiveData<UiStatusState> uiStatusState = _uiStatusState;

    private final MutableLiveData<UserInfoUiState> _uiState =
            new MutableLiveData<>(new UserInfoUiState());
    private final LiveData<UserInfoUiState> uiState = _uiState;

    public UserInfoViewModel(CreateUserInfoUseCase createUserInfoUseCase,
                             SignUpUseCase signUpUseCase) {
        this.createUserInfoUseCase = createUserInfoUseCase;
        this.signUpUseCase = signUpUseCase;
    }

    public LiveData<UserInfoUiState> getUiState() {
        return uiState;
    }

    public LiveData<UiStatusState> getUiStatusState() {
        return uiStatusState;
    }

    public void setName(String name) {
        var state = _uiState.getValue();
        state.setName(name);
        _uiState.setValue(state);
    }

    public void setGender(boolean isMale) {
        var state = _uiState.getValue();
        state.setMale(isMale);
        _uiState.setValue(state);
    }

    public void setDateOfBirth(long dateOfBirth) {
        var state = _uiState.getValue();
        state.setDateOfBirth(dateOfBirth);
        _uiState.setValue(state);
    }

    public void saveData() {
        var state = uiState.getValue();
        createUserInfoUseCase.execute(state.getName(), state.getDateOfBirth(), state.isMale());
        _uiStatusState.setValue(new UiStatusState.Success());
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateUserInfoUseCase createUserInfoUseCase;
        private final SignUpUseCase signUpUseCase;

        @Inject
        public Factory(CreateUserInfoUseCase createUserInfoUseCase,
                       SignUpUseCase signUpUseCase) {
            this.createUserInfoUseCase = createUserInfoUseCase;
            this.signUpUseCase = signUpUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new UserInfoViewModel(createUserInfoUseCase, signUpUseCase));
        }
    }
}