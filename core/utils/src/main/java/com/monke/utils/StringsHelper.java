package com.monke.utils;

public class StringsHelper {

    public static String getIntOrEmpty(Integer integer) {
        if (integer == null) return "";
        return String.valueOf(integer);
    }

    public static String getFloatOrEmpty(Float x) {
        if (x == null) return "";
        return String.valueOf(x);
    }
}
