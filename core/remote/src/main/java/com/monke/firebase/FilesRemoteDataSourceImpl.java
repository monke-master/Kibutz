package com.monke.firebase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.google.firebase.storage.FirebaseStorage;
import com.monke.data.FilesRemoteDataSource;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;
import com.monke.utils.ThreadUtils;

import javax.inject.Inject;

public class FilesRemoteDataSourceImpl implements FilesRemoteDataSource {

    private FirebaseStorage storage;

    @Inject
    public FilesRemoteDataSourceImpl(FirebaseStorage storage) {
        this.storage = storage;
    }

    @Override
    public void uploadFile(Uri uri, String path, OnCompleteListener<Result<?>> onCompleteListener) {
        ThreadUtils.runOnBackground(() -> {
            var ref = storage.getReference(path);
            ref.putFile(uri).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    onCompleteListener.onComplete(new Result.Success<>());
                } else {
                    onCompleteListener.onComplete(new Result.Failure<>(task.getException()));
                }
            });
        });
    }

    @Override
    public void getFileDownloadUrl(String path, OnCompleteListener<Result<String>> onCompleteListener) {
        ThreadUtils.runOnBackground(() -> {

        });
    }

}
