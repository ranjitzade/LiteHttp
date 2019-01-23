package com.ranjitzade.litehttp.lib.httploader;

import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;

/**
 * Created by ranjit
 */
public interface IHttpListener<T> {
    void onSuccess(T t);

    void onError(ErrorResponse response);
}
