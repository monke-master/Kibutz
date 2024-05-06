package com.monke.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.Identity;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.SaveProfileUseCase;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class EditProfileViewModel extends ViewModel {
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private SaveProfileUseCase saveProfileUseCase;

    private MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    private MutableLiveData<List<Identity>> _identities = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Identity>> identities = _identities;

    private MutableLiveData<List<String>> _photos = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<String>> photos = _photos;

    public EditProfileUiState uiState = new EditProfileUiState();

    public EditProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                                SaveProfileUseCase saveProfileUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.saveProfileUseCase = saveProfileUseCase;
        init();
    }

    public void init() {
        _user.setValue(getCurrentUserUseCase.execute());

        uiState.setBio(_user.getValue().getProfile().getBio());
        _identities.setValue(_user.getValue().getProfile().getIdentities());
    }

    public void addIdentities(List<Identity> identities) {
        var list = new ArrayList<Identity>(_identities.getValue());
        list.addAll(identities);
        _identities.setValue(list);
    }

    public void addPhoto(String uri) {
        var list = new ArrayList<>(_photos.getValue());
        list.add(uri);
        _photos.setValue(list);
    }

    public void save() {
        saveProfileUseCase.execute(uiState.getBio(), _identities.getValue());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ProfileViewModel", "CLEAR");
        ProfileComponentProvider.clear();
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetCurrentUserUseCase getCurrentUserUseCase;
        private final SaveProfileUseCase saveProfileUseCase;

        @Inject
        public Factory(GetCurrentUserUseCase getCurrentUserUseCase,
                       SaveProfileUseCase saveProfileUseCase) {
            this.getCurrentUserUseCase = getCurrentUserUseCase;
            this.saveProfileUseCase = saveProfileUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new EditProfileViewModel(getCurrentUserUseCase, saveProfileUseCase));
        }
    }
}