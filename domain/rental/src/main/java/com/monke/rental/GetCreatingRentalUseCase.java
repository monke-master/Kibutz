package com.monke.rental;

import javax.inject.Inject;

public class GetCreatingRentalUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public GetCreatingRentalUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public Rental execute() {
        return rentalRepository.getCreatingRental();
    }
}
