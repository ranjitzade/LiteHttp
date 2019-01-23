package com.ranjitzade.litehttp.lib.httploader;

import com.ranjitzade.litehttp.lib.core.IBaseLoader;
import com.ranjitzade.litehttp.lib.core.IHeaderListener;
import com.ranjitzade.litehttp.lib.core.Method;

import java.util.Map;

/**
 * Created by ranjit
 */
public interface IHttpLoader extends IBaseLoader {
    IHttpLoader url(String url);

    IHttpLoader header(Map<String, String> header);

    IHttpLoader body(Map<String, String> body);

    IHttpLoader clazz(Class<?> clazz);

    IHttpLoader method(Method method);

    IHttpLoader connectTimeout(int timeoutInMillis);

    IHttpLoader readTimeout(int timeoutInMillis);

    <T> IHttpLoader listener(IHttpListener<T> listener);

    IHttpLoader headerListener(IHeaderListener headerListener);


}