package com.example.notifydemo.utils;

import com.google.gson.Gson;

/**
 * Created by dequan.yu on 2020/3/12.
 */
public class GsonUtils {
    private static final Gson GSON = new Gson();

    public static String toJson(Object obj) {
        return GSON.toJson(obj);
    }

    public static <T> T fromJson(String json, Class<T> classOfT) {
        return GSON.fromJson(json, classOfT);
    }
}
