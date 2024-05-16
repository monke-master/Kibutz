package com.example.navigation;

import android.net.Uri;

import androidx.navigation.NavDeepLinkRequest;

public class UserNavigationContract {

    public static String USER_ID_KEY = "userId";

    public static String BASE_URI = "kibutz://com.monke.identity/pick_identities";

    public static NavDeepLinkRequest createDeepLinkRequest(String userId) {
        NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                .fromUri(Uri.parse(BASE_URI + "/" + USER_ID_KEY + "=" + userId))
                .build();
        return request;
    }
}
