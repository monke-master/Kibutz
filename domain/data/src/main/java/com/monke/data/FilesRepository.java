package com.monke.data;

import java.util.HashMap;
import java.util.List;

public interface FilesRepository {

    void uploadImages(String collection, List<String> imagesUrls, OnCompleteListener<Result<?>> listener);

    void getFileDownloadUrl(String path, OnCompleteListener<Result<String>> listener);

    void getFilesDownloadUrls(List<String> pathList, OnCompleteListener<Result<List<String>>> listener);

}
