package com.monke.rental;

import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

import java.util.List;

public interface RentalRemoteDataSource {

    void publishRental(RentalRemote rental, OnCompleteListener<Result<RentalRemote>> listener);

    void getRentalById(String id, OnCompleteListener<Result<RentalRemote>> listener);

    void getAvailableRentalsIds(String userId, OnCompleteListener<Result<List<String>>> listener);

}
