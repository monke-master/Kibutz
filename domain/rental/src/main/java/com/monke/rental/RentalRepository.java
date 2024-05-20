package com.monke.rental;

import androidx.lifecycle.LiveData;

import com.monke.data.Result;

import java.util.List;

public interface RentalRepository {

    void saveCreatingRental(Rental rental);

    Rental getCreatingRental();

    LiveData<Result<?>> publishRental(Rental rental);

    void updateRental(Rental rental);

    Rental getRentalById(String id);

    List<Rental> getRentals();
}
