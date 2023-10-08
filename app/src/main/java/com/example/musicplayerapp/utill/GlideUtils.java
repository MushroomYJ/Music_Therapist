package com.example.musicplayerapp.utill;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.musicplayerapp.R;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created byPing.zi on 2018/7/26. 10:10.
 */
public class GlideUtils {

    /*
     *Load drawable pics
     */
    public static void localImage(Context context, int drawable, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .dontTransform()
                .dontAnimate();
        Glide.with(context).load(drawable).apply(options).into(imageView);
    }

    /**
     * Load blurred images (custom transparency)
     *
     * @param context
     * @param url
     * @param imageView
     * @param ，         Ambiguity，1 - 100，the larger the blurrier.
     */
    public static void loadBlurImage(Context context, int url, ImageView imageView) {
        loadBlurImage(context, url, imageView, 30);
    }


    /**
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadBlurImage(Context context, int url, ImageView imageView, int radius) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                //.priority(Priority.HIGH)
                .bitmapTransform(new BlurTransformation(radius))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .dontAnimate();
        Glide.with(context).load(url).apply(options).into(imageView);
    }
}
