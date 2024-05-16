package com.monke.rental;

import android.util.Log;

import com.monke.di.RentalScope;

import javax.inject.Inject;

@RentalScope
public class RentalRepositoryImpl implements RentalRepository {

    private final String TAG = "RentalRepositoryImpl";
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
    public void publishRental(Rental rental) {

    }

    @Override
    public void updateRental(Rental rental) {
        Log.d(TAG, rental.toString());
    }
}
