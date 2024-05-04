package com.monke.profile;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.Identity;
import com.monke.user.GetAvailableIdentitiesUseCase;
import com.monke.user.GetCurrentUserUseCase;

import java.util.List;

import javax.inject.Inject;

public class IdentitiesViewModel extends ViewModel {

    private GetAvailableIdentitiesUseCase getAvailableIdentitiesUseCase;
    private GetCurrentUserUseCase getCurrentUserUseCase;

    private MutableLiveData<List<Identity>> _identities = new MutableLiveData<>();
    public LiveData<List<Identity>> identities = _identities;

    public IdentitiesViewModel(GetAvailableIdentitiesUseCase getAvailableIdentitiesUseCase,
                               GetCurrentUserUseCase getCurrentUserUseCase) {
        this.getAvailableIdentitiesUseCase = getAvailableIdentitiesUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public void init() {
        var user = getCurrentUserUseCase.execute();
        _identities.setValue(getAvailableIdentitiesUseCase.execute(user));
    }


    public static class Factory implements ViewModelProvider.Factory {

        private final GetAvailableIdentitiesUseCase getAvailableIdentitiesUseCase;
        private final GetCurrentUserUseCase getCurrentUserUseCase;

        @Inject
        public Factory(GetAvailableIdentitiesUseCase getAvailableIdentitiesUseCase,
                       GetCurrentUserUseCase getCurrentUserUseCase) {
            this.getAvailableIdentitiesUseCase = getAvailableIdentitiesUseCase;
            this.getCurrentUserUseCase = getCurrentUserUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new IdentitiesViewModel(getAvailableIdentitiesUseCase, getCurrentUserUseCase));
        }
    }
}
