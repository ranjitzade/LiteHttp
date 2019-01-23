package com.ranjitzade.litehttp.lib.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ranjitzade.litehttp.lib.cache.CacheManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ranjit
 */
public class LiteHttpUtils {
    public static final String TAG = "LiteHttp";

    public static final int CONNECT_TIMEOUT = 10 * 1000;
    public static final int READ_TIMEOUT = 30 * 1000;

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final static String STRING_NAME = "java.lang.String";
    private final static String BITMAP_NAME = "android.graphics.Bitmap";
    private final static String JSONOBJECT_NAME = "org.json.JSONObject";
    private static Gson gson;

    public static boolean isConnectedToInternet(Context ctx) {
        NetworkInfo networkInfo = null;
        if (ctx != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            if (connectivityManager != null) {
                networkInfo = connectivityManager.getActiveNetworkInfo();
            }
        }
        return networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected();
    }

    public static String addBodyToUrl(Map<String, String> body) {
        if (body == null) return "";
        StringBuilder builder = null;
        for (String key : body.keySet()) {
            if (builder == null) builder = new StringBuilder();
            else builder.append("&");

            try {
                builder.append(String.format("%s=%s", key, URLEncoder.encode(body.get(key), String.valueOf(UTF_8))));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
        return builder != null ? builder.toString() : "";
    }

    public static byte[] body2Byte(Map<String, String> body) {
        StringBuilder builder = null;

        for (String key : body.keySet()) {
            if (builder == null) builder = new StringBuilder();
            else builder.append("&");
            try {
                builder.append(String.format("%s=%s", key, URLEncoder.encode(body.get(key), String.valueOf(UTF_8))));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return builder != null ? builder.toString().getBytes() : new byte[0];
    }

    @SuppressWarnings("unchecked")
    public static Object responseFromInputStream(String url, InputStream inputStream, Class<?> clazz, CacheManager cache) {
        Object o;
        if (clazz.getName().equalsIgnoreCase(STRING_NAME)) {
            Object string = getString(inputStream);
            if (cache != null) {
                cache.put(url, string);
            }
            o = string;
        } else if (clazz.getName().equalsIgnoreCase(BITMAP_NAME)) {
            Object bitmap = getBitmap(inputStream);
            if (cache != null) {
                cache.put(url, bitmap);
            }
            o = bitmap;
        } else if (clazz.getName().equalsIgnoreCase(JSONOBJECT_NAME)) {
            Object string = getString(inputStream);
            if (cache != null) {
                cache.put(url, string);
            }
            o = getJSONObject((String) string);
        } else {
            o = getJson(url, inputStream, clazz, cache);
        }
        return o;
    }

    public static Object responseFromCache(Object object, Class<?> clazz) {
        Object o = null;
        if (clazz.getName().equalsIgnoreCase(STRING_NAME)) {
            o = object.toString();
        } else if (clazz.getName().equalsIgnoreCase(BITMAP_NAME)) {
            if (object instanceof Bitmap) {
                return o;
            }
        } else if (clazz.getName().equalsIgnoreCase(JSONOBJECT_NAME)) {
            o = getJSONObject(object.toString());
        } else {
            o = getJson(object.toString(), clazz);
        }
        return o;
    }

    @SuppressWarnings("unchecked")
    private static Object getJson(String url, InputStream inputStream, Class<?> clazz, CacheManager cache) {
        String streamToString = streamToString(inputStream);
        if (cache != null) {
            cache.put(url, streamToString);
        }
        return fromJsonString(streamToString, clazz);
    }

    private static Object getJson(String object, Class<?> clazz) {
        return fromJsonString(object, clazz);
    }

    private static Object getString(InputStream inputStream) {
        return streamToString(inputStream);
    }

    private static Object fromJsonString(String content, Class<?> clazz) {
        try {
            Object nextValue = new JSONTokener(content).nextValue();
            if (nextValue instanceof JSONObject) {
                return getGson().fromJson(content, clazz);
            } else if (nextValue instanceof JSONArray) {
                try {
                    List listObjects = new ArrayList<>();
                    JSONArray jsonArray = new JSONArray(content);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        listObjects.add(getGson().fromJson(jsonObject.toString(), clazz));
                    }
                    return listObjects;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String streamToString(InputStream inputStream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder builder = new StringBuilder();
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return builder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Object getBitmap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }

    private static Object getJSONObject(String s) {
        JSONObject object = null;
        try {
            object = new JSONObject(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static Gson getGson() {
        if (gson == null) {
            synchronized (LiteHttpUtils.class) {
                if (gson == null) {
                    gson = new GsonBuilder()
                            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                            .create();
                }
            }
        }
        return gson;
    }

    public static boolean isResponseFetched(int responseCode) {
        return responseCode >= 200 && responseCode < 300;
    }
}