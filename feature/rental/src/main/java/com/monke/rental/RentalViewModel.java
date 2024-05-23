package com.monke.rental;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.GetRentalFlatmatesUseCase;
import com.monke.user.GetResponseStatusUseCase;
import com.monke.user.RespondToRentalUseCase;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RentalViewModel extends ViewModel {

    private GetRentalByIdUseCase getRentalByIdUseCase;
    private GetRentalFlatmatesUseCase getRentalFlatmatesUseCase;
    private RespondToRentalUseCase respondToRentalUseCase;
    private GetCurrentUserUseCase getCurrentUserUseCase;
    private GetResponseStatusUseCase getResponseStatusUseCase;

    private MutableLiveData<Rental> _rental = new MutableLiveData<>();
    public LiveData<Rental> rental = _rental;

    private MutableLiveData<List<User>> _flatmates = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<User>> flatmates = _flatmates;

    private MutableLiveData<Response.Status> _responseStatus = new MutableLiveData<>(null);
    public LiveData<Response.Status> responseStatus = _responseStatus;

    private User user;
    private final String TAG = "RentalViewModel";

    public RentalViewModel(GetRentalByIdUseCase getRentalByIdUseCase,
                           GetRentalFlatmatesUseCase getRentalFlatmatesUseCase,
                           RespondToRentalUseCase respondToRentalUseCase,
                           GetCurrentUserUseCase getCurrentUserUseCase,
                           GetResponseStatusUseCase getResponseStatusUseCase) {
        this.getRentalByIdUseCase = getRentalByIdUseCase;
        this.getRentalFlatmatesUseCase = getRentalFlatmatesUseCase;
        this.respondToRentalUseCase = respondToRentalUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getResponseStatusUseCase = getResponseStatusUseCase;
    }

    public void setRentalId(String rentalId) {
        init(rentalId);
    }

    private void init(String rentalId) {
        user = getCurrentUserUseCase.execute().getValue();
        getRentalByIdUseCase.execute(rentalId).observeForever(rentalResult -> {
            if (rentalResult.isSuccess()) {
                var rental = rentalResult.get();
                _rental.setValue(rental);
                if (!userIfAuthor()) {
                    _responseStatus.setValue(getResponseStatusUseCase.execute(user, rental));
                }
            }
        });
    }

    public void respondToRental() {
        respondToRentalUseCase.execute(_rental.getValue());
    }

    public boolean userIfAuthor() {
        return user.getId().equals(_rental.getValue().getAuthorId());
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetRentalByIdUseCase getRentalByIdUseCase;
        private final GetRentalFlatmatesUseCase getRentalFlatmatesUseCase;
        private final RespondToRentalUseCase respondToRentalUseCase;
        private final GetCurrentUserUseCase getCurrentUserUseCase;
        private GetResponseStatusUseCase getResponseStatusUseCase;

        @Inject
        public Factory(GetRentalByIdUseCase getRentalByIdUseCase,
                       GetRentalFlatmatesUseCase getRentalFlatmatesUseCase,
                       RespondToRentalUseCase respondToRentalUseCase,
                       GetCurrentUserUseCase getCurrentUserUseCase,
                       GetResponseStatusUseCase getResponseStatusUseCase) {
            this.getRentalByIdUseCase = getRentalByIdUseCase;
            this.getRentalFlatmatesUseCase = getRentalFlatmatesUseCase;
            this.respondToRentalUseCase = respondToRentalUseCase;
            this.getCurrentUserUseCase = getCurrentUserUseCase;
            this.getResponseStatusUseCase = getResponseStatusUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RentalViewModel(
                    getRentalByIdUseCase,
                    getRentalFlatmatesUseCase,
                    respondToRentalUseCase,
                    getCurrentUserUseCase,
                    getResponseStatusUseCase));
        }
    }
}