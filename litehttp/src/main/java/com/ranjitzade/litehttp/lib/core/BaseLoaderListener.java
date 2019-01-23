package com.ranjitzade.litehttp.lib.core;

import android.os.Handler;
import android.os.Looper;

import com.ranjitzade.litehttp.lib.cache.CacheManager;

import androidx.annotation.Nullable;

/**
 * Created by ranjit
 */
public abstract class BaseLoaderListener implements ILoaderListener {
    @Nullable
    protected CacheManager mCache;
    protected Handler handler = new Handler(Looper.getMainLooper());

    public BaseLoaderListener(@Nullable CacheManager cache) {
        this.mCache = cache;
    }
}
