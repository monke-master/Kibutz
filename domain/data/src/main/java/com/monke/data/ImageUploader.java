package com.monke.data;

import java.util.HashMap;
import java.util.List;

public interface ImageUploader {

    void uploadImages(HashMap<String, String> images, OnCompleteListener listener);
}
