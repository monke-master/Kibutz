package com.monke.data;

import android.net.Uri;

import androidx.lifecycle.LiveData;

public interface FilesRemoteDataSource {

    void uploadFile(Uri uri, String path, OnCompleteListener<Result<?>> onCompleteListener);

    void getFileDownloadUrl(String path, OnCompleteListener<Result<String>> onCompleteListener);
}
