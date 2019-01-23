package com.ranjitzade.litehttp.lib.imageloader;

import android.graphics.Bitmap;

import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;

/**
 * Created by ranjit
 */
public interface IImageListener {
    void onSuccess(Bitmap bitmap);

    void onError(ErrorResponse errorCode);
}
