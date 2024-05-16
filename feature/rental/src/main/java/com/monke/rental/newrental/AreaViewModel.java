package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;

import javax.inject.Inject;

public class AreaViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;
    private AreaUiState areaUiState = new AreaUiState();

    public AreaViewModel(CreateRentalUseCase createRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
    }

    public AreaUiState getAreaUiState() {
        return areaUiState;
    }

    public void saveData() {
        createRentalUseCase.saveFlatArea(
                areaUiState.getArea(),
                areaUiState.getLivingArea(),
                areaUiState.getKitchenArea());
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
            return (T) (new AreaViewModel(createRentalUseCase));
        }
    }
}