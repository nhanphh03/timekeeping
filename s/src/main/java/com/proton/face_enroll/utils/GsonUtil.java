package com.proton.face_enroll.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class GsonUtil {
    public GsonUtil() {
    }

    private static final class GsonHolder {
        private static final Gson gson = (new GsonBuilder()).setLenient().disableHtmlEscaping().create();
    }

    public static Gson getGson() {

        return GsonHolder.gson;
    }
}

