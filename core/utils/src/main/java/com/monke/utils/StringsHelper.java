package com.monke.utils;

public class StringsHelper {

    public static String getFloatOrEmpty(Float x) {
        if (x == null) return "";
        return String.valueOf(x);
    }
}
