package com.monke.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.rental.Rental;
import com.monke.user.GetAvailableRentalsUseCase;

import java.util.List;

import javax.inject.Inject;

public class HomeViewModel extends ViewModel {

    private final GetAvailableRentalsUseCase getAvailableRentalsUseCase;

    private MutableLiveData<List<Rental>> _rentals = new MutableLiveData<>();
    public LiveData<List<Rental>> rentals = _rentals;

    public HomeViewModel(GetAvailableRentalsUseCase getAvailableRentalsUseCase) {
        this.getAvailableRentalsUseCase = getAvailableRentalsUseCase;
    }

    public void init() {
        fetchData();
    }

    private void fetchData() {
        getAvailableRentalsUseCase.execute().observeForever(listResult -> {
            if (listResult.isSuccess()) {
                _rentals.setValue(listResult.get());
            }
        });
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetAvailableRentalsUseCase getAvailableRentalsUseCase;

        @Inject
        public Factory(GetAvailableRentalsUseCase getAvailableRentalsUseCase) {
            this.getAvailableRentalsUseCase = getAvailableRentalsUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new HomeViewModel(getAvailableRentalsUseCase));
        }
    }
}