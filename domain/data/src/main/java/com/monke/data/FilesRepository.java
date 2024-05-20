package com.monke.data;

import java.util.HashMap;
import java.util.List;

public interface FilesRepository {

    void uploadImages(HashMap<String, String> imagesUrls, OnCompleteListener<Result<?>> listener);

    void getFileDownloadUrl(String path, OnCompleteListener<Result<String>> listener);

    HashMap<String, String> createUrlsToPath(List<String> urls);
}
