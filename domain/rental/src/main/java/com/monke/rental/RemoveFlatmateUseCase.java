package com.monke.rental;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class RemoveFlatmateUseCase {

    private final RentalRepository rentalRepository;

    @Inject
    public RemoveFlatmateUseCase(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public void execute(String rentalId, String userId) {
//        Rental rental = rentalRepository.getRentalById(rentalId);
//        List<String> flatmates = new ArrayList<>(rental.getFlatmatesIds());
//        flatmates.remove(userId);
//        rental.setFlatmatesIds(flatmates);
//
//        rentalRepository.updateRental(rental);
    }
}
