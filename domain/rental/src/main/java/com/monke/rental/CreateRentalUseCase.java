package com.monke.rental;

import java.util.UUID;

import javax.inject.Inject;

public class CreateRentalUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public CreateRentalUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(boolean isFlat) {
        Realty realty;
        if (isFlat) {
            realty = new Flat(UUID.randomUUID().toString());
        } else {
            realty = new House(UUID.randomUUID().toString());
        }
        Rental rental = new Rental(UUID.randomUUID().toString(), realty);
        rentalRepository.saveCreatingRental(rental);
    }
}
