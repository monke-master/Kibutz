package com.monke.rental;

import android.util.Log;

import com.monke.di.RentalScope;

import javax.inject.Inject;

@RentalScope
public class RentalCacheDataSourceImpl implements RentalCacheDataSource {

    private Rental creatingRental = null;

    @Inject
    public RentalCacheDataSourceImpl() {

    }

    @Override
    public void saveCreatingRental(Rental rental) {
        creatingRental = rental;
        Log.d("RentalCacheDataSourceImpl", creatingRental.toString());
    }

    @Override
    public Rental getCreatingRental() {
        return creatingRental;
    }
}
