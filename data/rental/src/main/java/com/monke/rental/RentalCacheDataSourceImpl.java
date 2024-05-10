package com.monke.rental;

import javax.inject.Inject;

public class RentalCacheDataSourceImpl implements RentalCacheDataSource {

    private Rental creatingRental = null;

    @Inject
    public RentalCacheDataSourceImpl() {

    }

    @Override
    public void saveCreatingRental(Rental rental) {
        creatingRental = rental;
    }

    @Override
    public Rental getCreatingRental() {
        return creatingRental;
    }
}
