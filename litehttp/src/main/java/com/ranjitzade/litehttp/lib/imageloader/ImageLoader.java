package com.ranjitzade.litehttp.lib.imageloader;

import android.content.Context;

import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;

import java.net.HttpURLConnection;
import java.net.URL;

import static com.ranjitzade.litehttp.lib.utils.LiteHttpUtils.CONNECT_TIMEOUT;
import static com.ranjitzade.litehttp.lib.utils.LiteHttpUtils.READ_TIMEOUT;

/**
 * Created by ranjit
 */
public class ImageLoader extends BaseImageLoader {
    public ImageLoader(Context context) {
        super(context);
    }

    @Override
    protected void callServer() {
        try {
            URL url = new URL(this.mUrl);
            mUrlConnection = (HttpURLConnection) url.openConnection();
            mUrlConnection.setConnectTimeout(CONNECT_TIMEOUT);
            mUrlConnection.setReadTimeout(READ_TIMEOUT);
            mUrlConnection.setUseCaches(true);
            mUrlConnection.connect();

            if (mUrlConnection.getResponseCode() == 200) {
                if (mLoaderListener != null) {
                    mLoaderListener.onSuccess(mUrl, mUrlConnection.getInputStream());
                }
            } else {
                if (mLoaderListener != null) {
                    ErrorResponse response = ErrorResponse.getErrorResponse(mUrlConnection.getResponseCode(), null, null);
                    mLoaderListener.onError(response);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            if (mLoaderListener != null) {
                ErrorResponse response = ErrorResponse.getErrorResponse(1, exception, null);
                if (mLoaderListener != null) {
                    mLoaderListener.onError(response);
                }
            }
        } finally {
            if (mUrlConnection != null) {
                mUrlConnection.disconnect();
            }
        }
    }
}