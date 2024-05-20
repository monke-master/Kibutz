package com.monke.firebase;

import androidx.lifecycle.LiveData;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.FilesRepository;
import com.monke.data.ImageUploader;
import com.monke.data.Result;

import java.util.HashMap;

import javax.inject.Inject;

public class FilesRepositoryImpl implements FilesRepository {

    private ImageUploader imageUploader;
    private FilesRemoteDataSource remoteDataSource;

    @Inject
    public FilesRepositoryImpl(ImageUploader imageUploader,
                               FilesRemoteDataSource remoteDataSource ) {
        this.imageUploader = imageUploader;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public LiveData<Result> uploadImages(HashMap<String, String> imagesUrls) {
        return null;
    }

    @Override
    public LiveData<Result<String>> getFileDownloadUrl(String path) {
        return null;
    }
}
