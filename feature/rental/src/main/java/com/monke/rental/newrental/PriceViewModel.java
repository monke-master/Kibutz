package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;

import javax.inject.Inject;

public class PriceViewModel extends ViewModel {

    private CreateRentalUseCase createRentalUseCase;
    private PriceUiState priceUiState = new PriceUiState();

    public PriceViewModel(CreateRentalUseCase createRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
    }

    public PriceUiState getPriceUiState() {
        return priceUiState;
    }

    public void saveData() {
        createRentalUseCase.savePrice(priceUiState.getPrice());
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
            return (T) (new PriceViewModel(createRentalUseCase));
        }
    }
}