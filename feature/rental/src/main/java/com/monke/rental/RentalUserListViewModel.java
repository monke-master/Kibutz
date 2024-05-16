package com.monke.rental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.SaveEmailUseCase;
import com.monke.user.User;

import javax.inject.Inject;

public class RentalUserListViewModel extends ViewModel {

    private GetCurrentUserUseCase getCurrentUserUseCase;

    public LiveData<User> user;

    public RentalUserListViewModel(GetCurrentUserUseCase getCurrentUserUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        user = getCurrentUserUseCase.execute();
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
            return (T) (new RentalUserListViewModel(getCurrentUserUseCase));
        }
    }
}