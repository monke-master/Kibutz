package com.monke.data;

import androidx.lifecycle.LiveData;

import java.util.HashMap;

public interface FilesRepository {

    LiveData<Result> uploadImages(HashMap<String, String> imagesUrls);

    LiveData<Result<String>> getFileDownloadUrl(String path);
}
