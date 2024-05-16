package com.monke.utils;

import android.content.Context;

public class StringsHelper {

    public static String getIntOrEmpty(Integer integer) {
        if (integer == null) return "";
        return String.valueOf(integer);
    }

    public static String getFloatOrEmpty(Float x) {
        if (x == null) return "";
        return String.valueOf(x);
    }

    public static String getLongOrEmpty(Long l) {
        if (l == null) return "";
        return String.valueOf(l);
    }
}
