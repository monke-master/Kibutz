package com.monke.rental;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AddFlatmateUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public AddFlatmateUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<?>> execute(String rentalId, String userId) {
        var result = new MutableLiveData<Result<?>>();
        rentalRepository.getRentalById(rentalId).observeForever(rentalRes -> {
            if (rentalRes.isFailure()) {
                result.setValue(rentalRes);
                return;
            }

            var rental = rentalRes.get().clone();
            List<String> flatmates = new ArrayList<>(rental.getFlatmatesIds());
            flatmates.add(userId);
            rental.setFlatmatesIds(flatmates);

            rentalRepository.updateRental(rentalRes.get(), rental).observeForever(result::setValue);
        });
        return result;
    }
}
