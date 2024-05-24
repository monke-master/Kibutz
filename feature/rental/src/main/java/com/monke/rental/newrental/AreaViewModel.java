package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.CreateRentalUseCase;
import com.monke.rental.Flat;
import com.monke.rental.GetCreatingRentalUseCase;
import com.monke.rental.Rental;

import javax.inject.Inject;

public class AreaViewModel extends ViewModel {

    private final CreateRentalUseCase createRentalUseCase;
    private final GetCreatingRentalUseCase getCreatingRentalUseCase;
    private AreaUiState areaUiState = new AreaUiState();

    private Rental rental;

    public AreaViewModel(CreateRentalUseCase createRentalUseCase,
                         GetCreatingRentalUseCase getCreatingRentalUseCase) {
        this.createRentalUseCase = createRentalUseCase;
        this.getCreatingRentalUseCase = getCreatingRentalUseCase;
    }

    public void init() {
        rental = getCreatingRentalUseCase.execute();
    }

    public AreaUiState getAreaUiState() {
        return areaUiState;
    }

    public void saveData() {
        if (rental.getRealty().isFlat()) {
            createRentalUseCase.saveFlatArea(
                    areaUiState.getArea(),
                    areaUiState.getLivingArea(),
                    areaUiState.getKitchenArea());
        } else {
            createRentalUseCase.saveHomeArea(
                    areaUiState.getArea(),
                    areaUiState.getPlotArea());
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
            return (T) (new AreaViewModel(createRentalUseCase, getCreatingRentalUseCase));
        }
    }
}