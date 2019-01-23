package com.ranjitzade.litehttp.lib.cache;

/**
 * Created by ranjit
 */
public interface CacheCallback<T> {
    void put(String key, T data);

    void clear(String key);

    T get(String key);

    void clearAll();
}