package com.monke.data;

import android.net.Uri;

import androidx.lifecycle.LiveData;

public interface FilesRemoteDataSource {

    LiveData<Result> uploadFile(Uri uri, String path);

    LiveData<Result<String>> getFileDownloadUrl(String path);
}
