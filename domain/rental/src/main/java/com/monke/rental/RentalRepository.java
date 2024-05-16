package com.monke.rental;

public interface RentalRepository {

    void saveCreatingRental(Rental rental);

    Rental getCreatingRental();

    void publishRental(Rental rental);

    void updateRental(Rental rental);
}
