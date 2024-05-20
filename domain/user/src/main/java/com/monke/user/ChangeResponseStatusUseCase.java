package com.monke.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.monke.rental.Rental;
import com.monke.rental.RentalRepository;
import com.monke.rental.Response;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ChangeResponseStatusUseCase {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;

    @Inject
    public ChangeResponseStatusUseCase(UserRepository userRepository,
                                       RentalRepository rentalRepository) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<Response>> execute(Response response, Response.Status newStatus) {
        var result = new MutableLiveData<Result<Response>>();

        Response newResponse = new Response(response);
        newResponse.setStatus(newStatus);
        rentalRepository.uploadResponse(newResponse, uploadRes -> {
            if (uploadRes.isFailure()) {
                result.setValue(new Result.Failure<>(uploadRes.getException()));
                return;
            }
            result.setValue(new Result.Success<>(newResponse));
        });

        return result;
    }
}
