package com.ranjitzade.litehttp.lib.httploader;

import android.content.Context;

import com.ranjitzade.litehttp.lib.core.Method;
import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;
import com.ranjitzade.litehttp.lib.utils.LiteHttpUtils;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by ranjit
 */
public class HttpLoader extends BaseHttpLoader {

    public HttpLoader(Context context) {
        super(context);
    }

    @Override
    protected void callServer() {
        URL url;

        if (mMethod == Method.GET) {
            try {
                url = new URL(this.mUrl + LiteHttpUtils.addBodyToUrl(mBody));
                mUrlConnection = (HttpURLConnection) url.openConnection();
                mUrlConnection.setConnectTimeout(mConnectTimeoutInMillis);
                mUrlConnection.setReadTimeout(mReadTimeoutInMillis);
                mUrlConnection.setUseCaches(true);
                mUrlConnection.setRequestMethod(mMethod.name());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else if (mMethod == Method.POST) {
            try {
                url = new URL(this.mUrl);
                mUrlConnection = (HttpURLConnection) url.openConnection();
                mUrlConnection.setConnectTimeout(mConnectTimeoutInMillis);
                mUrlConnection.setReadTimeout(mReadTimeoutInMillis);
                mUrlConnection.setDoOutput(true);
                mUrlConnection.setDoInput(true);
                mUrlConnection.setUseCaches(false);
                mUrlConnection.setRequestMethod(mMethod.name());
                mUrlConnection.setInstanceFollowRedirects(true);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            if (mLoaderListener != null) {
                mLoaderListener.onError(ErrorResponse.getErrorResponse(1, new RuntimeException(String.format("%s Method not found.", mMethod.name())), null));
            } else {
                throw new RuntimeException(String.format("%s Method not found.", mMethod.name()));
            }
        }

        for (Map.Entry<String, String> entry : mHeader.entrySet()) {
            mUrlConnection.addRequestProperty(entry.getKey(), entry.getValue());
        }

        try {
            if (mMethod == Method.POST && mBody != null && mBody.size() > 0) {
                DataOutputStream dos = new DataOutputStream(mUrlConnection.getOutputStream());
                dos.write(LiteHttpUtils.body2Byte(mBody));
                dos.flush();
                dos.close();
            }

            mUrlConnection.connect();

            if (LiteHttpUtils.isResponseFetched(mUrlConnection.getResponseCode())) {
                if (mHeaderListener != null)
                    mHeaderListener.onHeader(mUrlConnection.getHeaderFields());
                if (mLoaderListener != null) {
                    mLoaderListener.onSuccess(mUrl, mUrlConnection.getInputStream());
                }
            } else {
                ErrorResponse response = ErrorResponse.getErrorResponse(mUrlConnection.getResponseCode(), null, null);
                if (mLoaderListener != null) {
                    mLoaderListener.onError(response);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            if (mLoaderListener != null) {
                ErrorResponse response = ErrorResponse.getErrorResponse(1, exception, null);
                mLoaderListener.onError(response);
            }
        } finally {
            if (mUrlConnection != null) {
                mUrlConnection.disconnect();
            }
        }
    }
}