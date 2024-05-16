package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.SaveRentalUseCase;

import javax.inject.Inject;

public class PriceViewModel extends ViewModel {

    private SaveRentalUseCase saveRentalUseCase;
    private PriceUiState priceUiState = new PriceUiState();

    public PriceViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
    }

    public PriceUiState getPriceUiState() {
        return priceUiState;
    }

    public void saveData() {
        saveRentalUseCase.savePrice(priceUiState.getPrice());
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
            return (T) (new PriceViewModel(saveRentalUseCase));
        }
    }
}