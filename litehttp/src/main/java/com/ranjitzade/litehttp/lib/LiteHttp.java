package com.ranjitzade.litehttp.lib;

import android.content.Context;

import com.ranjitzade.litehttp.lib.httploader.HttpLoader;
import com.ranjitzade.litehttp.lib.httploader.IHttpLoader;
import com.ranjitzade.litehttp.lib.imageloader.IImageLoader;
import com.ranjitzade.litehttp.lib.imageloader.ImageLoader;

/**
 * Created by ranjit
 */
public class LiteHttp {

    public static IHttpLoader httpLoader(Context context) {
        return new HttpLoader(context);
    }

    public static IImageLoader imageLoader(Context context) {
        return new ImageLoader(context);
    }
}
