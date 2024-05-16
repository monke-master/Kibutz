package com.monke.rental.newrental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.Identity;
import com.monke.rental.SaveRentalUseCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class FlatmatesViewModel extends ViewModel {

    private SaveRentalUseCase saveRentalUseCase;
    private FlatmatesUiState uiState;
    private MutableLiveData<List<Identity>> _filters = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Identity>> filters = _filters;

    public FlatmatesViewModel(SaveRentalUseCase saveRentalUseCase) {
        this.saveRentalUseCase = saveRentalUseCase;
        uiState = new FlatmatesUiState();
    }

    public void addIdentityFilters(List<Identity> filters) {
        var identities = new ArrayList<>(_filters.getValue());
        identities.addAll(filters);
        _filters.setValue(identities);
    }

    public FlatmatesUiState getUiState() {
        return uiState;
    }

    public void saveData() {
        saveRentalUseCase
                .saveFlatmatesCount(uiState.getFlatmatesCount())
                .saveIdentitiesFilters(_filters.getValue());
    }

    public List<String> getFiltersIds() {
        return filters
                .getValue()
                .stream()
                .map(Identity::getId)
                .collect(Collectors.toList());
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