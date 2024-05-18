package com.monke.rental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.GetCurrentUserUseCase;
import com.monke.user.GetRentalFlatmatesUseCase;
import com.monke.user.GetUserRentalByIdUseCase;
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
    private Response.Status responseStatus;

    private MutableLiveData<Rental> _rental = new MutableLiveData<>();
    public LiveData<Rental> rental = _rental;

    private MutableLiveData<List<User>> _flatmates = new MutableLiveData<>(new ArrayList<>());
    public LiveData<List<User>> flatmates = _flatmates;

    public RentalViewModel(GetRentalByIdUseCase getRentalByIdUseCase,
                           GetRentalFlatmatesUseCase getRentalFlatmatesUseCase,
                           RespondToRentalUseCase respondToRentalUseCase,
                           GetCurrentUserUseCase getCurrentUserUseCase) {
        this.getRentalByIdUseCase = getRentalByIdUseCase;
        this.getRentalFlatmatesUseCase = getRentalFlatmatesUseCase;
        this.respondToRentalUseCase = respondToRentalUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public void setRentalId(String rentalId) {
        init(rentalId);
    }

    private void init(String rentalId) {
        _rental.setValue(getRentalByIdUseCase.execute(rentalId));
        _flatmates.setValue(getRentalFlatmatesUseCase.execute(_rental.getValue()));
    }

    public void respondToRental() {
        respondToRentalUseCase.execute(_rental.getValue());
    }

    public boolean userIfAuthor() {
        return getCurrentUserUseCase.execute().getValue().getId().equals(_rental.getValue().getAuthorId());
    }

    public void setResponseStatus(Response.Status responseStatus) {
        this.responseStatus = responseStatus;
    }

    public Response.Status getResponseStatus() {
        return responseStatus;
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetRentalByIdUseCase getRentalByIdUseCase;
        private final GetRentalFlatmatesUseCase getRentalFlatmatesUseCase;
        private RespondToRentalUseCase respondToRentalUseCase;
        private GetCurrentUserUseCase getCurrentUserUseCase;

        @Inject
        public Factory(GetRentalByIdUseCase getRentalByIdUseCase,
                       GetRentalFlatmatesUseCase getRentalFlatmatesUseCase,
                       RespondToRentalUseCase respondToRentalUseCase,
                       GetCurrentUserUseCase getCurrentUserUseCase) {
            this.getRentalByIdUseCase = getRentalByIdUseCase;
            this.getRentalFlatmatesUseCase = getRentalFlatmatesUseCase;
            this.respondToRentalUseCase = respondToRentalUseCase;
            this.getCurrentUserUseCase = getCurrentUserUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new RentalViewModel(
                    getRentalByIdUseCase,
                    getRentalFlatmatesUseCase,
                    respondToRentalUseCase,
                    getCurrentUserUseCase));
        }
    }
}