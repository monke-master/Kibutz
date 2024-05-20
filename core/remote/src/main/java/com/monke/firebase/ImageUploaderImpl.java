package com.monke.firebase;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.ImageUploader;
import com.monke.data.OnCompleteListener;

import java.util.HashMap;

import javax.inject.Inject;

public class ImageUploaderImpl implements ImageUploader {

    private final FilesRemoteDataSource filesRemoteDataSource;

    @Inject
    public ImageUploaderImpl(FilesRemoteDataSource filesRemoteDataSource) {
        this.filesRemoteDataSource = filesRemoteDataSource;
    }

    @Override
    public void uploadImages(HashMap<String, String> imagesUrls, OnCompleteListener listener) {

    }
}
