package com.monke.rental;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.monke.user.GetRentalFlatmatesUseCase;
import com.monke.user.GetUserByIdUseCase;
import com.monke.user.GetUserRentalByIdUseCase;
import com.monke.user.GetUsersFromResponsesUseCase;
import com.monke.user.RespondToRentalUseCase;
import com.monke.user.User;

import java.util.List;

import javax.inject.Inject;

public class ResponsesViewModel extends ViewModel {

    private final GetUserRentalByIdUseCase getRentalByIdUseCase;
    private final GetUsersFromResponsesUseCase getUsersFromResponsesUseCase;


    private MutableLiveData<List<User>> _users = new MutableLiveData<>();
    public LiveData<List<User>> users = _users;

    public ResponsesViewModel(GetUserRentalByIdUseCase getRentalByIdUseCase,
                              GetUsersFromResponsesUseCase getUsersFromResponsesUseCase) {
        this.getRentalByIdUseCase = getRentalByIdUseCase;
        this.getUsersFromResponsesUseCase = getUsersFromResponsesUseCase;
    }

    public void init(String rentalId) {
        Rental rental = getRentalByIdUseCase.execute(rentalId);
        _users.setValue(getUsersFromResponsesUseCase.execute(rental));

    }

    public static class Factory implements ViewModelProvider.Factory {

        private final GetUserRentalByIdUseCase getUserRentalByIdUseCase;
        private final GetUsersFromResponsesUseCase getUsersFromResponsesUseCase;

        @Inject
        public Factory(GetUserRentalByIdUseCase getUserRentalByIdUseCase,
                       GetUsersFromResponsesUseCase getUsersFromResponsesUseCase) {
            this.getUserRentalByIdUseCase = getUserRentalByIdUseCase;
            this.getUsersFromResponsesUseCase = getUsersFromResponsesUseCase;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) (new ResponsesViewModel(getUserRentalByIdUseCase,
                    getUsersFromResponsesUseCase));
        }
    }
}