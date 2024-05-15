package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.SaveRentalUseCase;

import javax.inject.Inject;

public class AreaViewModel extends ViewModel {

    private final SaveRentalUseCase saveRentalUseCase;
    private AreaUiState areaUiState = new AreaUiState();

    public AreaViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
    }

    public AreaUiState getAreaUiState() {
        return areaUiState;
    }

    public void saveData() {
        saveRentalUseCase.saveFlatArea(
                areaUiState.getArea(),
                areaUiState.getLivingArea(),
                areaUiState.getKitchenArea());
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
            return (T) (new AreaViewModel(saveRentalUseCase));
        }
    }
}