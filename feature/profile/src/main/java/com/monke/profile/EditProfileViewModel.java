package com.monke.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.identity.Identity;
import com.monke.identity.IdentityModel;
import com.monke.profile.di.ProfileComponentProvider;
import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.GetUserIdentitiesByTypeUseCase;
import com.monke.user.SaveProfileUseCase;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class EditProfileViewModel extends ViewModel {
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final SaveProfileUseCase saveProfileUseCase;
    private final GetUserIdentitiesByTypeUseCase getUserIdentitiesByTypeUseCase;

    private final MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    private final MutableLiveData<List<Identity>> _identities = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<Identity>> identities = _identities;

    private final MutableLiveData<List<String>> _photos = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<String>> photos = _photos;

    public EditProfileUiState uiState = new EditProfileUiState();

    public EditProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                                SaveProfileUseCase saveProfileUseCase,
                                GetUserIdentitiesByTypeUseCase getUserIdentitiesByTypeUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.saveProfileUseCase = saveProfileUseCase;
        this.getUserIdentitiesByTypeUseCase = getUserIdentitiesByTypeUseCase;
        init();
    }

    public void init() {
        _user.setValue(getCurrentUserUseCase.execute().getValue());

        uiState.setBio(_user.getValue().getProfile().getBio());
        _identities.setValue(getUserIdentitiesByTypeUseCase.execute(
                user.getValue(),
                List.of(Identity.Type.POSITIVE)));
        _photos.setValue(new ArrayList<>(_user.getValue().getProfile().getPhotosUrl()));
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

    public void removePhoto(int index) {
        var list = new ArrayList<>(_photos.getValue());
        list.remove(index);
        _photos.setValue(list);
    }

    public void save() {
        saveProfileUseCase.execute(uiState.getBio(), _identities.getValue(), _photos.getValue());
    }

    public ArrayList<String> getIdentitiesIds() {
        var list = _identities
                .getValue()
                .stream()
                .map(Identity::getId)
                .collect(Collectors.toList());
        return new ArrayList<>(list);
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
        private final GetUserIdentitiesByTypeUseCase getUserIdentitiesByTypeUseCase;

        @Inject
        public Factory(GetCurrentUserUseCase getCurrentUserUseCase,
                       SaveProfileUseCase saveProfileUseCase,
                       GetUserIdentitiesByTypeUseCase getUserIdentitiesByTypeUseCase) {
            this.getCurrentUserUseCase = getCurrentUserUseCase;
            this.saveProfileUseCase = saveProfileUseCase;
            this.getUserIdentitiesByTypeUseCase = getUserIdentitiesByTypeUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new EditProfileViewModel(
                    getCurrentUserUseCase,
                    saveProfileUseCase,
                    getUserIdentitiesByTypeUseCase));
        }
    }
}