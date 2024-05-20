package com.monke.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.GetUserByIdUseCase;
import com.monke.user.User;

import javax.inject.Inject;

public class UserViewModel extends ViewModel {

    private final GetUserByIdUseCase getUserByIdUseCase;

    private MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    public UserViewModel(GetUserByIdUseCase getUserByIdUseCase) {
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    public void init(String userId) {
        getUserByIdUseCase.execute(userId).observeForever(userResult -> {
            if (userResult.isSuccess()) {
                _user.setValue(userResult.get());
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetUserByIdUseCase getUserByIdUseCase;

        @Inject
        public Factory(GetUserByIdUseCase getUserByIdUseCase) {
            this.getUserByIdUseCase = getUserByIdUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new UserViewModel(getUserByIdUseCase));
        }
    }
}