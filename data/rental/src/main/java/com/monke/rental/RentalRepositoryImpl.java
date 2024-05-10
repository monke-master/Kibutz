package com.monke.rental;

import javax.inject.Inject;

public class RentalRepositoryImpl implements RentalRepository {

    private final RentalCacheDataSource cacheSource;

    @Inject
    public RentalRepositoryImpl(RentalCacheDataSource cacheSource) {
        this.cacheSource = cacheSource;
    }

    @Override
    public void saveCreatingRental(Rental rental) {
        cacheSource.saveCreatingRental(rental);
    }

    @Override
    public Rental getCreatingRental() {
        return cacheSource.getCreatingRental();
    }

    @Override
    public void publishRental() {

    }
}
