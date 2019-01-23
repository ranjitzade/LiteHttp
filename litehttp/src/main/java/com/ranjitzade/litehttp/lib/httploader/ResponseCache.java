package com.ranjitzade.litehttp.lib.httploader;

import com.ranjitzade.litehttp.lib.cache.CacheManager;

/**
 * Created by ranjit
 */
public class ResponseCache extends CacheManager<String> {
    private static volatile ResponseCache instance;

    public ResponseCache() {
        super();
    }

    public static synchronized ResponseCache getInstance() {
        if (instance == null) {
            synchronized (ResponseCache.class) {
                if (instance == null) {
                    instance = new ResponseCache();
                }
            }
        }
        return instance;
    }
}
