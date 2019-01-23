package com.ranjitzade.litehttp.lib.httploader;

import android.content.Context;
import android.util.Log;

import com.ranjitzade.litehttp.lib.core.BaseLoader;
import com.ranjitzade.litehttp.lib.core.IHeaderListener;
import com.ranjitzade.litehttp.lib.core.Method;
import com.ranjitzade.litehttp.lib.utils.LiteHttpUtils;

import java.util.Map;

import static com.ranjitzade.litehttp.lib.utils.LiteHttpUtils.CONNECT_TIMEOUT;
import static com.ranjitzade.litehttp.lib.utils.LiteHttpUtils.READ_TIMEOUT;

/**
 * Created by ranjit
 */
public abstract class BaseHttpLoader extends BaseLoader implements IHttpLoader {

    protected int mConnectTimeoutInMillis = CONNECT_TIMEOUT;
    protected int mReadTimeoutInMillis = READ_TIMEOUT;

    protected Method mMethod;
    protected IHttpListener mHttpListener = null;
    protected IHeaderListener mHeaderListener = null;
    protected Class<?> mClazz = null;

    public BaseHttpLoader(Context context) {
        super(context);
    }

    @Override
    public IHttpLoader url(String url) {
        this.mUrl = url;
        return this;
    }

    @Override
    public IHttpLoader header(Map<String, String> header) {
        this.mHeader.clear();
        this.mHeader.putAll(header);
        return this;
    }

    @Override
    public IHttpLoader body(Map<String, String> body) {
        this.mBody.clear();
        this.mBody.putAll(body);
        return this;
    }

    @Override
    public IHttpLoader clazz(Class<?> clazz) {
        this.mClazz = clazz;
        return this;
    }

    @Override
    public IHttpLoader method(Method method) {
        this.mMethod = method;
        return this;
    }

    @Override
    public <T> IHttpLoader listener(IHttpListener<T> listener) {
        this.mHttpListener = listener;
        return this;
    }

    @Override
    public IHttpLoader headerListener(IHeaderListener headerListener) {
        this.mHeaderListener = headerListener;
        return this;
    }

    @Override
    public IHttpLoader connectTimeout(int timeoutInMillis) {
        this.mConnectTimeoutInMillis = timeoutInMillis;
        return this;
    }

    @Override
    public IHttpLoader readTimeout(int timeoutInMillis) {
        this.mReadTimeoutInMillis = timeoutInMillis;
        return this;
    }

    @Override
    public boolean checkParameters() {
        if (mUrl == null) {
            throw new NullPointerException("Url cannot be null");
        }
        if (mClazz == null) {
            Log.w(LiteHttpUtils.TAG, "clazz is null");
            return false;
        }
        if (mHttpListener == null) {
            Log.w(LiteHttpUtils.TAG, "listener is null");
            return false;
        }
        //noinspection unchecked
        mLoaderListener = new HttpLoaderListener<>(mHttpListener, mClazz);
        return true;
    }
}