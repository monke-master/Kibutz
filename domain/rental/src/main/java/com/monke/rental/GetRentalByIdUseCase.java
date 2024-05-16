package com.monke.rental;

import javax.inject.Inject;

public class GetRentalByIdUseCase {

    private RentalRepository rentalRepository;

    @Inject
    public GetRentalByIdUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental execute(String id) {
        return rentalRepository.getRentalById(id);
    }
}
