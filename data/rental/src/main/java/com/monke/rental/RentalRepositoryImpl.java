package com.monke.rental;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.monke.data.Result;
import com.monke.di.AppScope;
import com.monke.user.Mocks;

import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.inject.Inject;

@AppScope
public class RentalRepositoryImpl implements RentalRepository {

    private final String TAG = "RentalRepositoryImpl";
    private final RentalCacheDataSource cacheSource;
    private final RentalRemoteDataSource remoteDataSource;

    private ArrayList<Rental> rentals = new ArrayList<>();

    @Inject
    public RentalRepositoryImpl(RentalCacheDataSource cacheSource, RentalRemoteDataSource remoteDataSource) {
        this.cacheSource = cacheSource;
        this.remoteDataSource = remoteDataSource;
        rentals.add(Mocks.mockRental);
        rentals.add(Mocks.mockRental2);
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
    public LiveData<Result<?>> publishRental(Rental rental) {
        MutableLiveData<Result<?>> result = new MutableLiveData<>();

        rentals.add(rental);
        return result;
    }

    @Override
    public void updateRental(Rental rental) {
        Log.d(TAG, rental.toString());
    }

    @Override
    public Rental getRentalById(String id) {
        return rentals.stream().filter(rental -> rental.getId().equals(id)).collect(Collectors.toList()).get(0);
    }

    @Override
    public ArrayList<Rental> getRentals() {
        return rentals;
    }
}
