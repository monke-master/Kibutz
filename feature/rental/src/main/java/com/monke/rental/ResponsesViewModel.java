package com.monke.rental;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.ChangeResponseStatusUseCase;
import com.monke.user.GetRentalUserResponsesUseCase;
import com.monke.user.GetUserRentalByIdUseCase;
import com.monke.user.RemoveResponseUseCase;
import com.monke.user.User;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ResponsesViewModel extends ViewModel {

    private final GetUserRentalByIdUseCase getRentalByIdUseCase;
    private final GetRentalUserResponsesUseCase getRentalUserResponsesUseCase;
    private final ChangeResponseStatusUseCase changeResponseStatusUseCase;
    private final RemoveResponseUseCase removeResponseUseCase;
    private final AddFlatmateUseCase addFlatmateUseCase;
    private final RemoveFlatmateUseCase removeFlatmateUseCase;

    private MutableLiveData<List<Pair<Response, User>>> _responses = new MutableLiveData<>();
    public LiveData<List<Pair<Response, User>>> responses = _responses;

    private String rentalId;

    public ResponsesViewModel(GetUserRentalByIdUseCase getRentalByIdUseCase,
                              GetRentalUserResponsesUseCase getRentalUserResponsesUseCase,
                              ChangeResponseStatusUseCase changeResponseStatusUseCase,
                              RemoveResponseUseCase removeResponseUseCase,
                              AddFlatmateUseCase addFlatmateUseCase,
                              RemoveFlatmateUseCase removeFlatmateUseCase) {
        this.getRentalByIdUseCase = getRentalByIdUseCase;
        this.getRentalUserResponsesUseCase = getRentalUserResponsesUseCase;
        this.changeResponseStatusUseCase = changeResponseStatusUseCase;
        this.removeResponseUseCase = removeResponseUseCase;
        this.addFlatmateUseCase = addFlatmateUseCase;
        this.removeFlatmateUseCase = removeFlatmateUseCase;
    }

    public void init(String rentalId) {
        this.rentalId = rentalId;
        fetchData();
    }

    private void fetchData() {
        Rental rental = getRentalByIdUseCase.execute(rentalId);
        _responses.setValue(new ArrayList<>(getRentalUserResponsesUseCase.execute(rental)));
    }

    public void changeResponseStatus(Response response, Response.Status status) {
        var newResponse = changeResponseStatusUseCase.execute(response, status);
        switch (status) {
            case DISLIKED -> removeResponseUseCase.execute(newResponse);
            case FLATMATE -> addFlatmateUseCase.execute(newResponse.getRentalId(), newResponse.getUserId());
            case LIKED -> removeFlatmatesIfNeeds(response, newResponse);
        }
        fetchData();
    }

    private void removeFlatmatesIfNeeds(Response oldResponse, Response newResponse) {
        if (oldResponse.getStatus().equals(Response.Status.FLATMATE)) {
            removeFlatmateUseCase.execute(newResponse.getRentalId(), newResponse.getUserId());
        }
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetUserRentalByIdUseCase getUserRentalByIdUseCase;
        private final GetRentalUserResponsesUseCase getRentalUserResponsesUseCase;
        private final ChangeResponseStatusUseCase changeResponseStatusUseCase;
        private final RemoveResponseUseCase removeResponseUseCase;
        private final AddFlatmateUseCase addFlatmateUseCase;
        private final RemoveFlatmateUseCase removeFlatmateUseCase;

        @Inject
        public Factory(GetUserRentalByIdUseCase getUserRentalByIdUseCase,
                       GetRentalUserResponsesUseCase getRentalUserResponsesUseCase,
                       ChangeResponseStatusUseCase changeResponseStatusUseCase,
                       RemoveResponseUseCase removeResponseUseCase,
                       AddFlatmateUseCase addFlatmateUseCase,
                       RemoveFlatmateUseCase removeFlatmateUseCase) {
            this.getUserRentalByIdUseCase = getUserRentalByIdUseCase;
            this.getRentalUserResponsesUseCase = getRentalUserResponsesUseCase;
            this.changeResponseStatusUseCase = changeResponseStatusUseCase;
            this.removeResponseUseCase = removeResponseUseCase;
            this.addFlatmateUseCase = addFlatmateUseCase;
            this.removeFlatmateUseCase = removeFlatmateUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new ResponsesViewModel(
                    getUserRentalByIdUseCase,
                    getRentalUserResponsesUseCase,
                    changeResponseStatusUseCase,
                    removeResponseUseCase,
                    addFlatmateUseCase,
                    removeFlatmateUseCase));
        }
    }
}