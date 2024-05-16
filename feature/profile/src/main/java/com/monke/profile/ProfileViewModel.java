package com.monke.profile;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.profile.di.ProfileComponentProvider;
import com.monke.rental.GetRentalByIdUseCase;
import com.monke.rental.Rental;
import com.monke.user.CreateUserInfoUseCase;
import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.User;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private GetCurrentUserUseCase getCurrentUserUseCase;
    private GetRentalByIdUseCase getRentalByIdUseCase;

    private MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    private MutableLiveData<List<Rental>> _responses = new MutableLiveData<>();
    public LiveData<List<Rental>> responses = _responses;

    @Inject
    public ProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                            GetRentalByIdUseCase getRentalByIdUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getRentalByIdUseCase = getRentalByIdUseCase;
    }

    public void init() {
        user = getCurrentUserUseCase.execute();
        List<Rental> rentals = user
                                .getValue()
                                .getResponses()
                                .stream()
                                .map(response -> getRentalByIdUseCase.execute(response.getRentalId()))
                                .collect(Collectors.toList());
        _responses.setValue(rentals);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ProfileViewModel", "CLEAR");
        ProfileComponentProvider.clear();
    }

    public static class Factory implements ViewModelProvider.Factory {

       private final GetCurrentUserUseCase getCurrentUserUseCase;
       private final GetRentalByIdUseCase getRentalByIdUseCase;

        @Inject
        public Factory(GetCurrentUserUseCase getCurrentUserUseCase,
                       GetRentalByIdUseCase getRentalByIdUseCase) {
            this.getCurrentUserUseCase = getCurrentUserUseCase;
            this.getRentalByIdUseCase = getRentalByIdUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new ProfileViewModel(getCurrentUserUseCase, getRentalByIdUseCase));
        }
    }
}