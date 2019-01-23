package com.ranjitzade.litehttp.lib.imageloader;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.ranjitzade.litehttp.lib.core.BaseLoader;
import com.ranjitzade.litehttp.lib.utils.LiteHttpUtils;

import androidx.core.content.ContextCompat;

/**
 * Created by ranjit
 */
public abstract class BaseImageLoader extends BaseLoader implements IImageLoader {

    protected ImageView mView;
    protected Drawable placeholder;
    protected Drawable errorDrawable;

    protected IImageListener mImageListener;
    protected ImageView.ScaleType mScaleType;

    public BaseImageLoader(Context context) {
        super(context);
    }

    @Override
    public IImageLoader url(String url) {
        this.mUrl = url;
        return this;
    }

    @Override
    public IImageLoader view(ImageView view) {
        this.mView = view;
        return this;
    }

    @Override
    public IImageLoader placeholder(int resId) {
        this.placeholder = ContextCompat.getDrawable(mContext, resId);
        return this;
    }

    @Override
    public IImageLoader error(int resId) {
        this.errorDrawable = ContextCompat.getDrawable(mContext, resId);
        return this;
    }

    @Override
    public IImageLoader scaleType(ImageView.ScaleType scaleType) {
        this.mScaleType = scaleType;
        return this;
    }

    @Override
    public IImageLoader listener(IImageListener listener) {
        this.mImageListener = listener;
        return this;
    }

    @Override
    public boolean checkParameters() {
        if (mUrl == null) {
            throw new NullPointerException("Url cannot be null");
        }
        if (mView == null) {
            Log.w(LiteHttpUtils.TAG, "image view is null");
            return false;
        }

        if (placeholder != null) {
            mView.setImageDrawable(placeholder);
        }
        mLoaderListener = new ImageLoaderListener(mImageListener, mView, errorDrawable, mScaleType);
        return true;
    }
}