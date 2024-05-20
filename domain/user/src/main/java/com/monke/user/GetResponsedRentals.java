package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.rental.Rental;
import com.monke.rental.RentalRepository;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class GetResponsedRentals {

    private RentalRepository rentalRepository;

    @Inject
    public GetResponsedRentals(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<List<Rental>>> execute(User user) {
        var result = new MutableLiveData<Result<List<Rental>>>();
        var list = new ArrayList<Rental>();
        if (user.getResponses().isEmpty()) {
            result.setValue(new Result.Success<>(list));
        }

        for (Response response: user.getResponses()) {
            rentalRepository.getRentalById(response.getRentalId(), rentalResult -> {
                if (rentalResult.isFailure()) {
                    result.setValue(new Result.Failure<>(rentalResult.getException()));
                    return;
                }

                list.add(rentalResult.get());
                if (list.size() == user.getResponses().size()) {
                    result.setValue(new Result.Success<>(list));
                }
            });
        }
        return result;
    }
}
