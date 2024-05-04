package com.monke.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.profile.di.ProfileComponentProvider;
import com.monke.user.CreateUserInfoUseCase;
import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.User;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private GetCurrentUserUseCase getCurrentUserUseCase;

    private MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    @Inject
    public ProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public void init() {
        _user.setValue(getCurrentUserUseCase.execute());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ProfileViewModel", "CLEAR");
        ProfileComponentProvider.clear();
    }

    public static class Factory implements ViewModelProvider.Factory {

       private final GetCurrentUserUseCase getCurrentUserUseCase;

        @Inject
        public Factory(GetCurrentUserUseCase getCurrentUserUseCase) {
            this.getCurrentUserUseCase = getCurrentUserUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new ProfileViewModel(getCurrentUserUseCase));
        }
    }
}