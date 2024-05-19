package com.example.navigation;

import android.net.Uri;
import android.util.Log;

import androidx.navigation.NavDeepLinkRequest;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import android.util.Base64;
import java.util.List;

public class PhotoNavigationContract {

    public static final String PHOTO_URI_KEY = "photo";

    public static String BASE_URI = "kibutz://com.monke.ui.photo/photo";

    public static NavDeepLinkRequest createDeepLinkRequest(String photoUri) {
        NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                .fromUri(Uri.parse(BASE_URI + "/" + getUriArg(photoUri)))
                .build();
        return request;
    }

    private static String getUriArg(String photoUri) {
        byte[] bytes = photoUri.getBytes(StandardCharsets.UTF_8);
        String encodedString = Base64.encodeToString(bytes, Base64.URL_SAFE);
        return PHOTO_URI_KEY + "=" + encodedString;
    }
}
