package com.monke.firebase;

import android.net.Uri;

import androidx.lifecycle.LiveData;

import com.monke.data.FilesRemoteDataSource;
import com.monke.data.Result;

import javax.inject.Inject;

public class FilesRemoteDataSourceImpl implements FilesRemoteDataSource {

    @Inject
    public FilesRemoteDataSourceImpl() {

    }


    @Override
    public LiveData<Result> uploadFile(Uri uri, String path) {
        return null;
    }

    @Override
    public LiveData<Result<String>> getFileDownloadUrl(String path) {
        return null;
    }
}
