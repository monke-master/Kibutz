package com.monke.firebase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.FilesRepository;
import com.monke.data.OnCompleteListener;
import com.monke.data.Result;

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
    public void uploadImages(HashMap<String, String> imagesUrls,
                             OnCompleteListener<Result<?>> listener) {
        imagesToLoad.set(imagesUrls.size());
        for (String imageUrl: imagesUrls.keySet()) {
            remoteDataSource.uploadFile(Uri.parse(imageUrl), imagesUrls.get(imageUrl), result -> {
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

    }

    @Override
    public HashMap<String, String> createUrlsToPath(List<String> urls) {
        HashMap<String, String> pathAndUrls = new HashMap<>();
        for (String url: urls) {
            pathAndUrls.put(url, UUID.randomUUID().toString());
        }
        return pathAndUrls;
    }
}
