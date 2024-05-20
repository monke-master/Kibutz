package com.monke.rental;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RemoveFlatmateUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public RemoveFlatmateUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<?>> execute(String rentalId, String userId) {

        var result = new MutableLiveData<Result<?>>();
        rentalRepository.getRentalById(rentalId).observeForever(rentalRes -> {
            if (rentalRes.isFailure()) {
                return;
            }

            var rental = rentalRes.get().clone();
            List<String> flatmates = new ArrayList<>(rental.getFlatmatesIds());
            flatmates.remove(userId);
            rental.setFlatmatesIds(flatmates);

            rentalRepository.updateRental(rentalRes.get(), rental).observeForever(result::setValue);
        });
        return result;
    }
}
