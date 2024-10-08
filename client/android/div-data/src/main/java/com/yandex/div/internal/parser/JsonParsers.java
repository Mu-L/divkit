package com.yandex.div.internal.parser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import kotlin.jvm.functions.Function1;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonParsers {

    @NonNull
    private static final ValueValidator<?> ALWAYS_VALID = (any) -> true;
    @NonNull
    private static final ValueValidator<String> ALWAYS_VALID_STRING = (any) -> true;
    @NonNull
    private static final ListValidator<?> ALWAYS_VALID_LIST = (any) -> true;
    @NonNull
    private static final Function1<?,?> AS_IS = (any) -> any;

    private JsonParsers() {
        /* do not initialize */
    }

    @Nullable
    static JSONObject nullable(@Nullable JSONObject json) {
        if (json == null || json == JSONObject.NULL) {
            return null;
        }

        return json;
    }

    @Nullable
    static <T> T optSafe(JSONObject json, String key) {
        Object value = json.opt(key);
        if (value == null || value == JSONObject.NULL) {
            return null;
        }

        return (T) value;
    }

    @Nullable
    static <T> T optSafe(JSONArray json, int index) {
        Object value = json.opt(index);
        if (value == null || value == JSONObject.NULL) {
            return null;
        }

        return (T) value;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <T> ValueValidator<T> alwaysValid() {
        return (ValueValidator<T>) ALWAYS_VALID;
    }

    @NonNull
    static ValueValidator<String> alwaysValidString() {
        return ALWAYS_VALID_STRING;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <T> ListValidator<T> alwaysValidList() {
        return (ListValidator<T>) ALWAYS_VALID_LIST;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    public static <T> Function1<T, T> doNotConvert() {
        return (Function1<T, T>) AS_IS;
    }
}
