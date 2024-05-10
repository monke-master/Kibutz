package com.monke.rental;

public interface RentalCacheDataSource {

    void saveCreatingRental(Rental rental);

    Rental getCreatingRental();
}
