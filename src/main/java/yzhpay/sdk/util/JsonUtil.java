package yzhpay.sdk.util;

import com.google.gson.Gson;

public class JsonUtil {

    private static Gson gson = new Gson();

    public static <T> T fromJson(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return gson.fromJson(json, clazz);
    }

    public static String toJson(Object object) {
        if (object == null) {
            return "";
        }
        return gson.toJson(object);
    }

}
