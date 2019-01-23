package com.ranjitzade.litehttp.lib.core;

import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;

import java.io.InputStream;

/**
 * Created by ranjit
 */
public interface ILoaderListener {
    void onSuccess(String url, InputStream inputStream);

    void onError(ErrorResponse response);

    boolean isLoadedFromCache(String url);
}
