package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.SaveRentalUseCase;

import javax.inject.Inject;

public class FloorViewModel extends ViewModel {

    private final SaveRentalUseCase saveRentalUseCase;
    private FloorUiState floorUiState;

    public FloorViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
        floorUiState = new FloorUiState();
    }

    public FloorUiState getFloorUiState() {
        return floorUiState;
    }

    public void saveData() {
        saveRentalUseCase
                .saveFloorCount(floorUiState.getFloorCount())
                .saveFlatFloor(floorUiState.getFloor());
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
            return (T) (new FloorViewModel(saveRentalUseCase));
        }
    }
}