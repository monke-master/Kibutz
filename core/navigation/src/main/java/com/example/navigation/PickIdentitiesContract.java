package com.example.navigation;

import android.net.Uri;

import androidx.navigation.NavDeepLinkRequest;

import com.monke.identity.Identity;

import java.util.List;
import java.util.stream.Collectors;

public class PickIdentitiesContract {
    public static String RESULT_KEY = "CHIPS_RESULT";
    public static String IDENTITIES_KEY = "IDENTITIES";
    public static String UNAVAILABLE_IDS_KEY = "unavailableIdentities";
    public static String IDENTITIES_TYPES_KEY = "identitiesTypes";

    public static String BASE_URI = "kibutz://com.monke.identity/pick_identities";


    public static NavDeepLinkRequest createDeepLinkRequest(List<String> unavailableIds,
                                                           List<String> requiredTypes) {
        String uri = BASE_URI + "/?" + idsParams(unavailableIds);
        if (!unavailableIds.isEmpty()) {
            uri += "&";
        }
        uri += requiredTypesParams(requiredTypes);
        NavDeepLinkRequest request = NavDeepLinkRequest.Builder
                .fromUri(Uri.parse(uri))
                .build();

        return request;
    }

    private static String idsParams(List<String> unavailableIds) {
        return unavailableIds
                .stream()
                .map(s -> UNAVAILABLE_IDS_KEY + "=" + s)
                .collect(Collectors.joining("&"));
    }
    private static String requiredTypesParams(List<String> requiredTypes) {
        return requiredTypes
                .stream()
                .map(s -> IDENTITIES_TYPES_KEY + "=" + s)
                .collect(Collectors.joining("&"));
    }
}
