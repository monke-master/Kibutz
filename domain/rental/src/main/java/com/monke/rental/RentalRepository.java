package com.monke.rental;

public interface RentalRepository {

    void saveCreatingRental(Rental rental);

    Rental getCreatingRental();

    void publishRental();
}
