package com.monke.profile;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.Identity;
import com.monke.user.GetAvailablePositiveIdentitiesUseCase;
import com.monke.user.GetCurrentUserUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class IdentitiesViewModel extends ViewModel {

    private GetAvailablePositiveIdentitiesUseCase getAvailablePositiveIdentitiesUseCase;
    private GetCurrentUserUseCase getCurrentUserUseCase;

    private MutableLiveData<List<Identity>> _identities = new MutableLiveData<>();
    public LiveData<List<Identity>> identities = _identities;

    private ArrayList<Identity> selectedIdentities = new ArrayList<>();

    public IdentitiesViewModel(GetAvailablePositiveIdentitiesUseCase getAvailablePositiveIdentitiesUseCase,
                               GetCurrentUserUseCase getCurrentUserUseCase) {
        this.getAvailablePositiveIdentitiesUseCase = getAvailablePositiveIdentitiesUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public void init() {
        var user = getCurrentUserUseCase.execute();
        _identities.setValue(getAvailablePositiveIdentitiesUseCase.execute(user));
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

    public static class Factory implements ViewModelProvider.Factory {

        private final GetAvailablePositiveIdentitiesUseCase getAvailablePositiveIdentitiesUseCase;
        private final GetCurrentUserUseCase getCurrentUserUseCase;

        @Inject
        public Factory(GetAvailablePositiveIdentitiesUseCase getAvailablePositiveIdentitiesUseCase,
                       GetCurrentUserUseCase getCurrentUserUseCase) {
            this.getAvailablePositiveIdentitiesUseCase = getAvailablePositiveIdentitiesUseCase;
            this.getCurrentUserUseCase = getCurrentUserUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new IdentitiesViewModel(getAvailablePositiveIdentitiesUseCase, getCurrentUserUseCase));
        }
    }
}
