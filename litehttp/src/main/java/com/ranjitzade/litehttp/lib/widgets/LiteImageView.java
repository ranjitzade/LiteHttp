package com.ranjitzade.litehttp.lib.widgets;

import android.content.Context;
import android.util.AttributeSet;

import com.ranjitzade.litehttp.lib.LiteHttp;
import com.ranjitzade.litehttp.lib.imageloader.IImageListener;

import androidx.annotation.DrawableRes;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by ranjit
 */
public class LiteImageView extends AppCompatImageView {
    public LiteImageView(Context context) {
        super(context);
    }

    public LiteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LiteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void loadImage(String url) {
        loadImage(url, 0);
    }

    public void loadImage(String url, @DrawableRes int placeholder) {
        loadImage(url, placeholder, 0);
    }

    public void loadImage(String url, @DrawableRes int placeholder, @DrawableRes int error) {
        loadImage(url, placeholder, error, null);
    }

    public void loadImage(String url, @DrawableRes int placeholder, @DrawableRes int error, IImageListener listener) {
        LiteHttp.imageLoader(getContext())
                .url(url)
                .scaleType(getScaleType())
                .placeholder(placeholder)
                .error(error)
                .view(this)
                .listener(listener)
                .execute();
    }
}
