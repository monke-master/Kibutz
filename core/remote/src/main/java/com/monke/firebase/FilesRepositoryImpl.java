package com.monke.firebase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.FilesRepository;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;

public class FilesRepositoryImpl implements FilesRepository {

    private FilesRemoteDataSource remoteDataSource;
    private volatile AtomicInteger imagesToLoad = new AtomicInteger();

    @Inject
    public FilesRepositoryImpl(FilesRemoteDataSource remoteDataSource ) {
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public void uploadImages(String collection, List<String> imagesUrls,
                             OnCompleteListener<Result<?>> listener) {
        if (imagesUrls.isEmpty()) {
            listener.onComplete(new Result.Success<>());
            return;
        }
        imagesToLoad.set(imagesUrls.size());
        for (String imageUrl: imagesUrls) {
            var path = collection + "/" + imageUrl;
            remoteDataSource.uploadFile(Uri.parse(imageUrl), path, result -> {
                if (result.isFailure()) {
                    listener.onComplete(result);
                    return;
                }
                imagesToLoad.getAndDecrement();
                if (imagesToLoad.get() == 0) listener.onComplete(new Result.Success<>());
             });
        }
    }

    @Override
    public void getFileDownloadUrl(String path, OnCompleteListener<Result<String>> listener) {
        remoteDataSource.getFileDownloadUrl(path, listener);
    }

    @Override
    public void getFilesDownloadUrls(List<String> pathList,
                                     OnCompleteListener<Result<List<String>>> listener) {
        if (pathList.isEmpty()) {
            listener.onComplete(new Result.Success<>(Collections.emptyList()));
            return;
        }
        imagesToLoad.set(pathList.size());
        ArrayList<String> urls = new ArrayList<>();
        for (String path: pathList) {
            remoteDataSource.getFileDownloadUrl(path, result -> {
                if (result.isFailure()) {
                    listener.onComplete(new Result.Failure<>(result.getException()));
                    return;
                }
                imagesToLoad.getAndDecrement();
                urls.add(result.get());
                if (imagesToLoad.get() == 0) listener.onComplete(new Result.Success<>(urls));
            });
        }
    }
}
