package com.monke.rental;

import java.util.List;

public interface RentalRepository {

    void saveCreatingRental(Rental rental);

    Rental getCreatingRental();

    void publishRental(Rental rental);

    void updateRental(Rental rental);

    Rental getRentalById(String id);

    List<Rental> getRentals();
}
