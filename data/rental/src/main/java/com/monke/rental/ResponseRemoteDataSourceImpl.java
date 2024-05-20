package com.monke.rental;

import com.google.firebase.firestore.FirebaseFirestore;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

import javax.inject.Inject;

public class ResponseRemoteDataSourceImpl implements ResponseRemoteDataSource {

    private FirebaseFirestore firestore;
    private final String RESPONSE_COLLECTION = "responses";

    @Inject
    public ResponseRemoteDataSourceImpl(FirebaseFirestore firestore) {
        this.firestore = firestore;
    }

    @Override
    public void uploadResponse(ResponseRemote response, OnCompleteListener<Result<?>> listener) {
        firestore
                .collection(RESPONSE_COLLECTION)
                .document(response.responseId)
                .set(response)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        listener.onComplete(new Result.Success<>());
                    } else {
                        listener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }

    @Override
    public void getResponseId(String responseId, OnCompleteListener<Result<ResponseRemote>> listener) {
        firestore
                .collection(RESPONSE_COLLECTION)
                .document(responseId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        var response = task.getResult().toObject(ResponseRemote.class);
                        listener.onComplete(new Result.Success<>(response));
                    } else {
                        listener.onComplete(new Result.Failure<>(task.getException()));
                    }
                });
    }
}
