package com.monke.rental;

import android.util.Log;
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

    private final GetRentalByIdUseCase getRentalByIdUseCase;
    private final GetRentalUserResponsesUseCase getRentalUserResponsesUseCase;
    private final ChangeResponseStatusUseCase changeResponseStatusUseCase;
    private final RemoveResponseUseCase removeResponseUseCase;
    private final AddFlatmateUseCase addFlatmateUseCase;
    private final RemoveFlatmateUseCase removeFlatmateUseCase;

    private MutableLiveData<List<Pair<Response, User>>> _responses = new MutableLiveData<>();
    public LiveData<List<Pair<Response, User>>> responses = _responses;

    private String rentalId;

    public ResponsesViewModel(GetRentalByIdUseCase getRentalByIdUseCase,
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
        getRentalByIdUseCase.execute(rentalId).observeForever(rentalRes -> {
            if (rentalRes.isFailure()) {
                return;
            }
            Log.d("ResponsesViewModel", rentalRes.get().toString());
            var rental = rentalRes.get();
            getRentalUserResponsesUseCase.execute(rental).observeForever(result -> {
                if (result.isSuccess()) {
                    Log.d("ResponsesViewModel", result.get().size() + "");
                    _responses.setValue(result.get());
                }
            });
        });

    }

    public void changeResponseStatus(Response response, Response.Status status) {
        changeResponseStatusUseCase.execute(response, status).observeForever(responseResult -> {
            if (responseResult.isSuccess()) {
                var newResponse = responseResult.get();
                switch (status) {
                    case DISLIKED -> removeResponseUseCase
                                            .execute(newResponse)
                                            .observeForever(r -> fetchData());
                    case FLATMATE ->
                            addFlatmateUseCase
                                    .execute(newResponse.getRentalId(), newResponse.getUserId())
                                    .observeForever(r -> fetchData());
                    case LIKED -> removeFlatmatesIfNeeds(response, newResponse);
                    default -> fetchData();
                }
            }
        });

    }

    private void removeFlatmatesIfNeeds(Response oldResponse, Response newResponse) {
        if (oldResponse.getStatus().equals(Response.Status.FLATMATE)) {
            removeFlatmateUseCase
                    .execute(newResponse.getRentalId(), newResponse.getUserId())
                    .observeForever(r -> fetchData());
        } else {
            fetchData();
        }
    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetRentalByIdUseCase getUserRentalByIdUseCase;
        private final GetRentalUserResponsesUseCase getRentalUserResponsesUseCase;
        private final ChangeResponseStatusUseCase changeResponseStatusUseCase;
        private final RemoveResponseUseCase removeResponseUseCase;
        private final AddFlatmateUseCase addFlatmateUseCase;
        private final RemoveFlatmateUseCase removeFlatmateUseCase;

        @Inject
        public Factory(GetRentalByIdUseCase getUserRentalByIdUseCase,
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