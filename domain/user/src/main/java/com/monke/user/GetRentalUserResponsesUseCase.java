package com.monke.user;

import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.monke.rental.Rental;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class GetRentalUserResponsesUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetRentalUserResponsesUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public LiveData<Result<List<Pair<Response, User>>>> execute(Rental rental) {
        var result = new MutableLiveData<Result<List<Pair<Response, User>>>>();
        ArrayList<Pair<Response, User>> list = new ArrayList<>();
        for (Response response: rental.getResponses()) {
            userRepository.getUserById(response.getUserId()).observeForever(userResult -> {
                if (userResult.isFailure()) {
                    result.setValue(new Result.Failure<>(userResult.getException()));
                    return;
                }

                list.add(new Pair<>(response, userResult.get()));
                if (list.size() == rental.getResponses().size()) {
                    result.setValue(new Result.Success<>(list));
                }
            });
        }
        return result;
    }
}
