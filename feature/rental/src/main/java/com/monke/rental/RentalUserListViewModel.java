package com.monke.rental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.SaveEmailUseCase;

import javax.inject.Inject;

public class RentalUserListViewModel extends ViewModel {


    public static class Factory implements ViewModelProvider.Factory {

        private final SaveEmailUseCase saveEmailUseCase;

        @Inject
        public Factory(SaveEmailUseCase saveEmailUseCase) {
            this.saveEmailUseCase = saveEmailUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RentalUserListViewModel());
        }
    }
}