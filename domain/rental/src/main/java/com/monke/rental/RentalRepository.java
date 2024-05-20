package com.monke.rental;

import androidx.lifecycle.LiveData;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

import java.util.List;

public interface RentalRepository {

    void saveCreatingRental(Rental rental);

    Rental getCreatingRental();

    LiveData<Result<?>> publishRental(Rental rental);

    LiveData<Result<?>> updateRental(Rental oldRental, Rental rental);

    LiveData<Result<Rental>> getRentalById(String id);

    List<Rental> getRentals();

    LiveData<Result<List<Rental>>> getUserRentals(List<String> rentalIds);

    void getRentalById(String id, OnCompleteListener<Result<Rental>> listener);

    void uploadResponse(Response response, OnCompleteListener<Result<?>> listener);

    void getResponses(List<String> responsesIds, OnCompleteListener<Result<List<Response>>> listener);

    LiveData<Result<List<Rental>>> getAvailableRentals(String userId);
}
