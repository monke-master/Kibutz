package com.monke.rental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.GetRentalFlatmatesUseCase;
import com.monke.user.GetUserRentalByIdUseCase;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RentalViewModel extends ViewModel {

    private GetUserRentalByIdUseCase getUserRentalByIdUseCase;
    private GetRentalFlatmatesUseCase getRentalFlatmatesUseCase;

    private MutableLiveData<Rental> _rental = new MutableLiveData<>();
    public LiveData<Rental> rental = _rental;

    private MutableLiveData<List<User>> _flatmates = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<User>> flatmates = _flatmates;

    public RentalViewModel(GetUserRentalByIdUseCase getUserRentalByIdUseCase,
                           GetRentalFlatmatesUseCase getRentalFlatmatesUseCase) {
        this.getUserRentalByIdUseCase = getUserRentalByIdUseCase;
        this.getRentalFlatmatesUseCase = getRentalFlatmatesUseCase;
    }

    public void setRentalId(String rentalId) {
        init(rentalId);
    }

    private void init(String rentalId) {
        _rental.setValue(getUserRentalByIdUseCase.execute(rentalId));
        _flatmates.setValue(getRentalFlatmatesUseCase.execute(_rental.getValue()));
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetUserRentalByIdUseCase getUserRentalByIdUseCase;
        private final GetRentalFlatmatesUseCase getRentalFlatmatesUseCase;

        @Inject
        public Factory(GetUserRentalByIdUseCase getUserRentalByIdUseCase,
                       GetRentalFlatmatesUseCase getRentalFlatmatesUseCase) {
            this.getUserRentalByIdUseCase = getUserRentalByIdUseCase;
            this.getRentalFlatmatesUseCase = getRentalFlatmatesUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RentalViewModel(getUserRentalByIdUseCase, getRentalFlatmatesUseCase));
        }
    }
}