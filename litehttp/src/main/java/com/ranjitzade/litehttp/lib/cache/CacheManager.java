package com.ranjitzade.litehttp.lib.cache;

import android.util.LruCache;

/**
 * Created by ranjit
 */
public class CacheManager<T> implements CacheCallback<T> {
    private static final int maxResourceSize = (int) Runtime.getRuntime().maxMemory() / 1024;
    private static final int cacheSize = maxResourceSize / 8;
    private LruCache<String, T> mLruCache;

    public CacheManager() {
        mLruCache = new LruCache<String, T>(cacheSize) {
            @Override
            public void trimToSize(int maxSize) {
                super.trimToSize(maxSize);
            }
        };
    }

    @Override
    public void put(String key, T data) {
        try {
            if (mLruCache.get(key) == null) {
                mLruCache.put(key, data);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void clear(String key) {
        if (mLruCache.get(key) != null) {
            mLruCache.remove(key);
        }
    }

    @Override
    public T get(String key) {
        return mLruCache.get(key);
    }

    @Override
    public void clearAll() {
        mLruCache.evictAll();
    }
}