package com.monke.rental;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import javax.inject.Inject;

public class GetRentalByIdUseCase {

    private RentalRepository rentalRepository;

    @Inject
    public GetRentalByIdUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public LiveData<Result<Rental>> execute(String id) {
        return rentalRepository.getRentalById(id);
    }
}
