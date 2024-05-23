package com.monke.rental;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

public class RentalRemoteDataSourceImpl implements RentalRemoteDataSource {

    private final FirebaseFirestore firestore;
    private final String RENTAL_COLLECTION = "rentals";
    private final int DATA_LIMIT = 10000;

    @Inject
    public RentalRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
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

    @Override
    public void getAvailableRentalsIds(String userId, OnCompleteListener<Result<List<String>>> listener) {
        firestore
                .collection(RENTAL_COLLECTION)
                .whereNotEqualTo("authorId", userId)
                .orderBy("creationDate", Query.Direction.DESCENDING)
                .limit(DATA_LIMIT)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        var rentals = task
                                .getResult()
                                .getDocuments()
                                .stream()
                                .map(doc -> doc.getId())
                                .collect(Collectors.toList());
                        listener.onComplete(new Result.Success<>(rentals));
                    } else {
                        listener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }
}
