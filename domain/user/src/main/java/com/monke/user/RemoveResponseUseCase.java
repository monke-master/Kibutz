package com.monke.user;

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

    public void execute(Response response) {
        Rental rental = rentalRepository.getRentalById(response.getRentalId());
        List<Response> rentalResponses = new ArrayList<>(rental.getResponses());
        rentalResponses.remove(response);
        rental.setResponses(rentalResponses);

        rentalRepository.updateRental(rental);
    }
 }
