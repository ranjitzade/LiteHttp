package com.ranjitzade.litehttp.lib.imageloader;

import android.graphics.Bitmap;

import com.ranjitzade.litehttp.lib.cache.CacheManager;

/**
 * Created by ranjit
 */
public class ImageCache extends CacheManager<Bitmap> {
    private static volatile ImageCache instance;

    public ImageCache() {
        super();
    }

    public static synchronized ImageCache getInstance() {
        if (instance == null) {
            synchronized (ImageCache.class) {
                if (instance == null) {
                    instance = new ImageCache();
                }
            }
        }
        return instance;
    }
}
