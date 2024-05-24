package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;
import com.monke.rental.GetCreatingRentalUseCase;
import com.monke.rental.Rental;

import javax.inject.Inject;

public class FloorViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;
    private final GetCreatingRentalUseCase getCreatingRentalUseCase;
    private FloorUiState floorUiState;
    private Rental rental;

    public FloorViewModel(CreateRentalUseCase createRentalUseCase,
                          GetCreatingRentalUseCase getCreatingRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        this.getCreatingRentalUseCase = getCreatingRentalUseCase;
        floorUiState = new FloorUiState();
        rental = getCreatingRentalUseCase.execute();
    }

    public FloorUiState getFloorUiState() {
        return floorUiState;
    }

    public void saveData() {
        createRentalUseCase
                .saveFloorCount(floorUiState.getFloorCount());
        if (isFlat()) {
            createRentalUseCase.saveFlatFloor(floorUiState.getFloor());
        }
    }

    public boolean isFlat() {
        return rental.getRealty().isFlat();
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final CreateRentalUseCase createRentalUseCase;
        private final GetCreatingRentalUseCase getCreatingRentalUseCase;

        @Inject
        public Factory(CreateRentalUseCase createRentalUseCase,
                       GetCreatingRentalUseCase getCreatingRentalUseCase) {
            this.createRentalUseCase = createRentalUseCase;
            this.getCreatingRentalUseCase = getCreatingRentalUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new FloorViewModel(createRentalUseCase, getCreatingRentalUseCase));
        }
    }
}