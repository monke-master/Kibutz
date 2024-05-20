package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.monke.rental.Rental;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetRentalFlatmatesUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetRentalFlatmatesUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<List<User>>> execute(Rental rental) {
        var result = new MutableLiveData<Result<List<User>>>();
        ArrayList<User> users = new ArrayList<>();

        for (String id: rental.getFlatmatesIds()) {
            userRepository.getUserById(id).observeForever(userResult -> {
                if (userResult.isFailure()) {
                    result.setValue(new Result.Failure<>(userResult.getException()));
                    return;
                }

                users.add(userResult.get());
                if (users.size() == rental.getFlatmatesIds().size()) {
                    result.setValue(new Result.Success<>(users));
                }
            });
        }

        return result;
    }
}
