package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;
import com.monke.rental.RentalViewModel;
import com.monke.user.SaveEmailUseCase;

import javax.inject.Inject;

public class RentalTypeViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;

    public RentalTypeViewModel(CreateRentalUseCase createRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
    }


    public void setRentalType(boolean isFlat) {
        createRentalUseCase.create(isFlat);
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;

        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase) {
            this.createRentalUseCase = createRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RentalTypeViewModel(createRentalUseCase));
        }
    }
}