package com.xingguang.master.view;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xingguang.master.R;

/**
 * 图片加载工具类
 *
 * @author BiHaidong
 * @description Glide 封装
 * @date 2017-4-25
 */
public class ImageLoader {

    //  加载失败图片
    private static int errorImageView = R.mipmap.load_faile;

    private static RequestManager requestManager;

    private volatile static ImageLoader instance;

    /**
     * Returns singleton class instance
     */
    public static ImageLoader getInstance() {
        if (instance == null) {
            synchronized (ImageLoader.class) {
                if (instance == null) {
                    instance = new ImageLoader();
                }
            }
        }
        return instance;
    }

    protected ImageLoader() {

    }

    /**
     * Begin a load with Glide by passing in a context.
     *
     * @see #initGlide(Activity)
     * @see #initGlide(android.app.Fragment))
     * @see #initGlide(android.support.v4.app.Fragment))
     * @see #initGlide(android.support.v4.app.FragmentActivity))
     */
    public ImageLoader initGlide(Context mContext) {
        requestManager = Glide.with(mContext);
        return instance;
    }

    /**
     * Begin a load with Glide that will be tied to the given {@link Activity}'s lifecycle and that uses the
     * given {@link Activity}'s default options.
     *
     * @return A RequestManager for the given activity that can be used to start a load.
     */
    public ImageLoader initGlide(Activity mContext) {
        requestManager = Glide.with(mContext);
        return instance;
    }

    /**
     * Begin a load with Glide that will tied to the give {@link android.support.v4.app.FragmentActivity}'s lifecycle
     * and that uses the given {@link android.support.v4.app.FragmentActivity}'s default options.
     *
     * @param activity The activity to use.
     * @return A RequestManager for the given FragmentActivity that can be used to start a load.
     */
    public ImageLoader initGlide(FragmentActivity activity) {
        requestManager = Glide.with(activity);
        return instance;
    }

    /**
     * Begin a load with Glide that will be tied to the given {@link android.app.Fragment}'s lifecycle and that uses
     * the given {@link android.app.Fragment}'s default options.
     *
     * @param fragment The fragment to use.
     * @return A RequestManager for the given Fragment that can be used to start a load.
     */
    public ImageLoader initGlide(android.app.Fragment fragment) {
        requestManager = Glide.with(fragment);
        return instance;
    }

    /**
     * Begin a load with Glide that will be tied to the given {@link android.support.v4.app.Fragment}'s lifecycle and
     * that uses the given {@link android.support.v4.app.Fragment}'s default options.
     *
     * @param fragment The fragment to use.
     * @return A RequestManager for the given Fragment that can be used to start a load.
     */
    public ImageLoader initGlide(android.support.v4.app.Fragment fragment) {
        requestManager = Glide.with(fragment);
        return instance;
    }

    public void loadImage(String url, ImageView mImageView) {
        if (requestManager != null)
            requestManager.load(url).error(errorImageView).animate(android.R.anim.fade_in).crossFade().into(mImageView);
    }

    public void loadImage(int imagerResuce, ImageView mImageView) {
        if (requestManager != null)
            requestManager.load(imagerResuce).error(errorImageView).animate(android.R.anim.fade_in).crossFade().into(mImageView);
    }

    public void loadImage(String url, ImageView mImageView, int errorImage) {
        if (requestManager != null)
            requestManager.load(url).error(errorImage).animate(android.R.anim.fade_in).crossFade().into(mImageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView mImageView) {
        Glide.with(context).load(url).override(100,100).
                dontAnimate().error(errorImageView).animate(android.R.anim.fade_in).transform(new GlideCircleTransform(context)).into(mImageView);
    }

    public static void loadCircleImage(Context context, Integer resourceId, ImageView mImageView) {
        Glide.with(context).load(resourceId).override(100,100).dontAnimate().error(errorImageView).animate(android.R.anim.fade_in).transform(new GlideCircleTransform(context)).into(mImageView);
    }

    public static void loadCircleImage(Context context, String url, ImageView mImageView, int errorImage) {
        Glide.with(context).load(url).dontAnimate().error(errorImage).animate(android.R.anim.fade_in).transform(new GlideCircleTransform(context)).into(mImageView);
    }
    public static void loadCircleImage(Context context, Integer resourceId, ImageView mImageView, int errorImage) {
        Glide.with(context).load(resourceId).dontAnimate().error(errorImage).animate(android.R.anim.fade_in).transform(new GlideCircleTransform(context)).into(mImageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView mImageView, int round) {
        Glide.with(context).load(url).error(errorImageView).animate(android.R.anim.fade_in).transform(new GlideRoundTransform(context, round)).into(mImageView);
    }

    public static void loadRoundImage(Context context, String url, ImageView mImageView, int round, int errorImage) {
        Glide.with(context).load(url).error(errorImage).animate(android.R.anim.fade_in).transform(new GlideRoundTransform(context, round)).into(mImageView);
    }

    public static void loadRoundImage(Context context, int img, ImageView mImageView, int round) {
//        if (requestManager != null)
//            requestManager.load(img).error(errorImageView).animate(android.R.anim.fade_in).crossFade().into(mImageView);
        Glide.with(context).load(img).error(errorImageView).animate(android.R.anim.fade_in).transform(new GlideRoundTransform(context, round)).into(mImageView);
    }


    public static void loadRoundYuanjiaoImage(Context context,String url, ImageView mImageView){
        Glide.with(context)
                .load(url)
                .transform(new CenterCrop(context), new GlideRoundTransform(context))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .crossFade()
                .into(mImageView);
    }


}
