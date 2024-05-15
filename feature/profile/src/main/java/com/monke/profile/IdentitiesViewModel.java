package com.monke.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.FilterIdentitiesByTypeUseCase;
import com.monke.identity.GetIdentitiesUseCase;
import com.monke.identity.Identity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

public class IdentitiesViewModel extends ViewModel {

    private FilterIdentitiesByTypeUseCase filterUseCase;
    private GetIdentitiesUseCase getIdentitiesUseCase;

    private MutableLiveData<List<Identity>> _identities = new MutableLiveData<>();
    public LiveData<List<Identity>> identities = _identities;

    private ArrayList<Identity> selectedIdentities = new ArrayList<>();
    private List<String> unavailableIdentities = Collections.emptyList();
    private List<Identity.Type> requiredTypes;

    public IdentitiesViewModel(FilterIdentitiesByTypeUseCase filterUseCase,
                               GetIdentitiesUseCase getIdentitiesUseCase) {
        this.filterUseCase = filterUseCase;
        this.getIdentitiesUseCase = getIdentitiesUseCase;
    }

    public void init() {
        _identities.setValue(filterUseCase.execute(
                getIdentitiesUseCase.execute(), requiredTypes, unavailableIdentities));
    }

    public void onIdentityStatusChanged(Identity identity, boolean isSelected) {
        if (isSelected) {
            selectedIdentities.add(identity);
        } else {
            selectedIdentities.remove(identity);
        }
    }

    public ArrayList<Identity> getSelectedIdentities() {
        return selectedIdentities;
    }

    public void setUnavailableIdentities(List<String> identities) {
        this.unavailableIdentities = identities;
    }

    public void setRequiredTypes(List<Identity.Type> requiredTypes) {
        this.requiredTypes = requiredTypes;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private FilterIdentitiesByTypeUseCase filterUseCase;
        private GetIdentitiesUseCase getIdentitiesUseCase;

        @Inject
        public Factory(FilterIdentitiesByTypeUseCase filterUseCase,
                       GetIdentitiesUseCase getIdentitiesUseCase) {
            this.filterUseCase = filterUseCase;
            this.getIdentitiesUseCase = getIdentitiesUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new IdentitiesViewModel(filterUseCase, getIdentitiesUseCase));
        }
    }
}
