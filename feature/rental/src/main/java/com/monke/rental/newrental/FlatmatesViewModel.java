package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.Identity;
import com.monke.rental.SaveRentalUseCase;

import java.util.ArrayList;

import javax.inject.Inject;

public class FlatmatesViewModel extends ViewModel {

    private SaveRentalUseCase saveRentalUseCase;
    private FlatmatesUiState uiState;

    public FlatmatesViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
        uiState = new FlatmatesUiState();
    }

    public void addIdentityFilter(Identity identity) {
        var identities = new ArrayList<>(uiState.getIdentityFilters());
        identities.add(identity);
        uiState.setIdentityFilters(identities);
    }

    public FlatmatesUiState getUiState() {
        return uiState;
    }

    public void saveData() {
        saveRentalUseCase
                .saveFlatmatesCount(uiState.getFlatmatesCount())
                .saveIdentitiesFilters(uiState.getIdentityFilters());
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
            return (T) (new FlatmatesViewModel(saveRentalUseCase));
        }
    }
}