package com.ranjitzade.litehttp.lib.imageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.ranjitzade.litehttp.lib.core.BaseLoaderListener;
import com.ranjitzade.litehttp.lib.core.response.ErrorResponse;

import java.io.InputStream;

/**
 * Created by ranjit
 */
public class ImageLoaderListener extends BaseLoaderListener {
    private final IImageListener mImageListener;
    private final ImageView mView;
    private final Drawable errorDrawable;
    private final ImageView.ScaleType mScaleType;

    public ImageLoaderListener(IImageListener imageListener, ImageView view,
                               Drawable errorDrawable, ImageView.ScaleType scaleType) {
        super(ImageCache.getInstance());
        this.mImageListener = imageListener;
        this.mView = view;
        this.errorDrawable = errorDrawable;
        this.mScaleType = scaleType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onSuccess(String url, InputStream inputStream) {
        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        if (bitmap != null) {
            if (mCache != null) {
                mCache.put(url, bitmap);
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setBitmapToView(bitmap);
                }
            });
        } else {
            ErrorResponse response = ErrorResponse.getErrorResponse(1, null, "Bitmap returned null");
            onError(response);
        }

        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void onError(final ErrorResponse response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (mView != null && errorDrawable != null) {
                    if (mScaleType != null && mView.getScaleType() != mScaleType) {
                        mView.setScaleType(mScaleType);
                    }
                    mView.setImageDrawable(errorDrawable);
                }
                if (mImageListener != null) {
                    mImageListener.onError(response);
                }
            }
        });
    }

    @Override
    public boolean isLoadedFromCache(String url) {
        if (mCache == null) {
            return false;
        }
        try {
            final Bitmap bitmap = (Bitmap) mCache.get(url);
            if (bitmap == null) {
                return false;
            }
            handler.post(new Runnable() {
                @Override
                public void run() {
                    setBitmapToView(bitmap);
                }
            });
            return true;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }

    private void setBitmapToView(Bitmap bitmap) {
        if (mView != null) {
            if (mScaleType != null && mView.getScaleType() != mScaleType) {
                mView.setScaleType(mScaleType);
            }
            mView.setImageBitmap(bitmap);
        }
        if (mImageListener != null) {
            mImageListener.onSuccess(bitmap);
        }
    }
}