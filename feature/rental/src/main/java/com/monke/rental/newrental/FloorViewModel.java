package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;

import javax.inject.Inject;

public class FloorViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;
    private FloorUiState floorUiState;

    public FloorViewModel(CreateRentalUseCase createRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        floorUiState = new FloorUiState();
    }

    public FloorUiState getFloorUiState() {
        return floorUiState;
    }

    public void saveData() {
        createRentalUseCase
                .saveFloorCount(floorUiState.getFloorCount())
                .saveFlatFloor(floorUiState.getFloor());
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
            return (T) (new FloorViewModel(createRentalUseCase));
        }
    }
}