package com.ranjitzade.litehttp.lib.httploader;

import com.ranjitzade.litehttp.lib.core.BaseLoaderListener;
import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;

import java.io.IOException;
import java.io.InputStream;

import static com.ranjitzade.litehttp.lib.utils.LiteHttpUtils.responseFromCache;
import static com.ranjitzade.litehttp.lib.utils.LiteHttpUtils.responseFromInputStream;

/**
 * Created by ranjit
 */
public class HttpLoaderListener<T> extends BaseLoaderListener {
    private IHttpListener<T> mListener;
    private Class<T> mClazz;

    public HttpLoaderListener(IHttpListener<T> listener, Class<T> clazz) {
        super(ResponseCache.getInstance());
        this.mListener = listener;
        this.mClazz = clazz;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSuccess(String url, InputStream inputStream) {
        final T response = (T) responseFromInputStream(url, inputStream, mClazz, mCache);
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onSuccess(response);
                }
            }
        });
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(final ErrorResponse response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.onError(response);
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean isLoadedFromCache(String url) {
        if (mListener == null || mCache == null) {
            return false;
        }
        try {
            Object object = mCache.get(url);
            if (object == null) {
                return false;
            }
            final T response = (T) responseFromCache(object, mClazz);
            if (response == null) {
                return false;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mListener.onSuccess(response);
                }
            });

            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}