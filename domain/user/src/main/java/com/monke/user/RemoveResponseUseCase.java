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

public class RemoveResponseUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public RemoveResponseUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<?>> execute(Response response) {
        var result = new MutableLiveData<Result<?>>();
        rentalRepository.getRentalById(response.getRentalId()).observeForever(rentalRes -> {
            if (rentalRes.isFailure()) {
                result.setValue(rentalRes);
                return;
            }

            var rental = rentalRes.get().clone();
            List<Response> rentalResponses = new ArrayList<>(rental.getResponses());
            rentalResponses.remove(response);
            rental.setResponses(rentalResponses);

            rentalRepository.updateRental(rentalRes.get(), rental).observeForever(result::setValue);
        });
        return result;
    }
 }
