package com.ranjitzade.litehttp.lib.core;

import android.content.Context;
import android.util.Log;

import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;
import com.ranjitzade.litehttp.lib.utils.LiteHttpUtils;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.FutureTask;

import androidx.annotation.Nullable;

/**
 * Created by ranjit
 */
public abstract class BaseLoader implements IBaseLoader {
    private static final String TAG = "BaseLoader";
    protected HttpURLConnection mUrlConnection;
    @Nullable
    protected ILoaderListener mLoaderListener;

    protected Context mContext;
    protected String mUrl = null;
    protected Map<String, String> mHeader = new HashMap<>();
    protected Map<String, String> mBody = new HashMap<>();

    public BaseLoader(Context context) {
        this.mContext = context;
    }

    @Override
    public void run() {
        if (mContext == null) {
            throw new RuntimeException("You forgot to call with(Context) method.");
        }

        if (checkParameters()) {
            boolean isLoadedFromCache = false;
            if (mLoaderListener != null) {
                isLoadedFromCache = mLoaderListener.isLoadedFromCache(mUrl);
            }
            Log.e(TAG, "isLoadedFromCache " + isLoadedFromCache);

            if (!isLoadedFromCache) {
                if (LiteHttpUtils.isConnectedToInternet(mContext)) {
                    callServer();
                } else {
                    if (mLoaderListener != null) {
                        mLoaderListener.onError(ErrorResponse.getNoInternetResponse());
                    }
                }
            }
        }
    }

    @Override
    public void execute() {
        ThreadPoolManager.getInstance().execute(new FutureTask<>(this, null));
    }

    protected abstract void callServer();


}
