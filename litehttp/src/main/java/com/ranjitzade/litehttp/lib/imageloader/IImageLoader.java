package com.ranjitzade.litehttp.lib.imageloader;

import android.widget.ImageView;

import com.ranjitzade.litehttp.lib.core.IBaseLoader;

import androidx.annotation.DrawableRes;

/**
 * Created by ranjit
 */
public interface IImageLoader extends IBaseLoader {
    IImageLoader url(String url);

    IImageLoader view(ImageView view);

    IImageLoader placeholder(@DrawableRes int resId);

    IImageLoader error(@DrawableRes int resId);

    IImageLoader scaleType(ImageView.ScaleType scaleType);

    IImageLoader listener(IImageListener listener);
}