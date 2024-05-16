package com.monke.rental;

import android.util.Log;

import com.monke.di.RentalScope;
import com.monke.user.Mocks;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

@RentalScope
public class RentalRepositoryImpl implements RentalRepository {

    private final String TAG = "RentalRepositoryImpl";
    private final RentalCacheDataSource cacheSource;

    private ArrayList<Rental> rentals = new ArrayList<>();

    @Inject
    public RentalRepositoryImpl(RentalCacheDataSource cacheSource) {
        this.cacheSource = cacheSource;
        rentals.add(Mocks.mockRental);
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
        rentals.add(rental);
    }

    @Override
    public void updateRental(Rental rental) {
        Log.d(TAG, rental.toString());
    }

    @Override
    public Rental getRentalById(String id) {
        return rentals.stream().filter(rental -> rental.getId().equals(id)).collect(Collectors.toList()).get(0);
    }
}
