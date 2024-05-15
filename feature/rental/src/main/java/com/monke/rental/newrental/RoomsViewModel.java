package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;
import com.monke.rental.SaveRentalUseCase;

import javax.inject.Inject;

public class RoomsViewModel extends ViewModel {

    private final SaveRentalUseCase saveRentalUseCase;

    public RoomsViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
    }

    public void saveRoomsCount(int count) {
        saveRentalUseCase.saveRoomsCount(count);
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final SaveRentalUseCase saveRentalUseCase;

        @Inject
        public Factory(SaveRentalUseCase saveRentalUseCase) {
            this.saveRentalUseCase = saveRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RoomsViewModel(saveRentalUseCase));
        }
    }
}