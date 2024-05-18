package com.monke.rental;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class AddFlatmateUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public AddFlatmateUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(String rentalId, String userId) {
        Rental rental = rentalRepository.getRentalById(rentalId);
        List<String> flatmates = new ArrayList<>(rental.getFlatmatesIds());
        flatmates.add(userId);
        rental.setFlatmatesIds(flatmates);

        rentalRepository.updateRental(rental);
    }
}
