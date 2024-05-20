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
import com.monke.rental.Response;
import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.GetUserRentalByIdUseCase;
import com.monke.user.User;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private GetCurrentUserUseCase getCurrentUserUseCase;
    private GetUserRentalByIdUseCase getRentalByIdUseCase;

    private MutableLiveData<User> _user = new MutableLiveData<>();
    public LiveData<User> user = _user;

    private MutableLiveData<List<Rental>> _rentals = new MutableLiveData<>();
    public LiveData<List<Rental>> rentals = _rentals;

    public HashMap<String, Response> responses;

    @Inject
    public ProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                            GetUserRentalByIdUseCase getRentalByIdUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getRentalByIdUseCase = getRentalByIdUseCase;
    }

    public void init() {
        responses = new HashMap<>();
        user = getCurrentUserUseCase.execute();

        var rentalsList = user.getValue().getResponses().stream().map(response -> {
            var rental = getRentalByIdUseCase.execute(response.getRentalId());
            responses.put(rental.getId(), response);
            return rental;
        }).collect(Collectors.toList());
        _rentals.setValue(rentalsList);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.d("ProfileViewModel", "CLEAR");
        ProfileComponentProvider.clear();
    }

    public static class Factory implements ViewModelProvider.Factory {

       private final GetCurrentUserUseCase getCurrentUserUseCase;
       private final GetUserRentalByIdUseCase getRentalByIdUseCase;

        @Inject
        public Factory(GetCurrentUserUseCase getCurrentUserUseCase,
                       GetUserRentalByIdUseCase getRentalByIdUseCase) {
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