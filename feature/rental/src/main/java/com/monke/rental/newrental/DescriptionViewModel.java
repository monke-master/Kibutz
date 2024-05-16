package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.SaveRentalUseCase;

import javax.inject.Inject;

public class DescriptionViewModel extends ViewModel {

    private final SaveRentalUseCase saveRentalUseCase;
    private String description;

    public DescriptionViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void saveData() {
        saveRentalUseCase.saveDescription(description);
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
            return (T) (new DescriptionViewModel(saveRentalUseCase));
        }
    }
}