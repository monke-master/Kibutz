package com.monke.rental;

import android.util.Log;

import com.google.firebase.firestore.FirebaseFirestore;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.user.Mocks;
import com.monke.user.UserRemote;

import javax.inject.Inject;

public class RentalRemoteDataSourceImpl implements RentalRemoteDataSource {

    private final FirebaseFirestore firestore;
    private final String RENTAL_COLLECTION = "rentals";

    @Inject
    public RentalRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
        publishRental(new RentalRemote(Mocks.mockRental), result -> {
            getRentalById(Mocks.mockRental.getId(), result1 -> {
                Log.d("RentalRemoteDataSourceImpl",  result1.get().toDomain().toString());
            });
        });
    }

    @Override
    public void publishRental(RentalRemote rental, OnCompleteListener<Result<RentalRemote>> listener) {
        firestore
                .collection(RENTAL_COLLECTION)
                .document(rental.id)
                .set(rental)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onComplete(new Result.Success<>());
                    } else {
                        listener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }

    @Override
    public void getRentalById(String id, OnCompleteListener<Result<RentalRemote>> listener) {
        firestore
                .collection(RENTAL_COLLECTION)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        var rental = task.getResult().toObject(RentalRemote.class);
                        listener.onComplete(new Result.Success<>(rental));
                    } else {
                        listener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }
}
