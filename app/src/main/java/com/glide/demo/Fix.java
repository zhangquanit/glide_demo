package com.glide.demo;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


/**
 * @author 张全
 */

public class Fix {
    /**
     * 有的图片第一次加载的时候只显示占位图，第二次才显示正常的图片呢？
     *
     * @param mContext
     * @param url
     */
    public void loadRoundImag(Context mContext,String url){
        Glide.with(mContext)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        // setImageDrawable(resource) on CircleImageView
                    }
                });
    }
}
