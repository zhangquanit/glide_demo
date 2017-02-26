package com.glide.demo;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.module.GlideModule;
import com.glide.demo.loader.MyDataModel;
import com.glide.demo.loader.MyUrlLoader;

import java.io.InputStream;

/**
 * @author 张全
 */

public class MyGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        /**
         * 设置disk cache 缓存路径、缓存大小
         * 默认是InternalCacheDiskCacheFactory，/data/{package}/cache目录下，大小250MB
         *    File cacheDirectory = context.getCacheDir();
         *    new File(cacheDirectory, diskCacheName); //diskCacheName默认是image_manager_disk_cache
         * 也可以使用ExternalCacheDiskCacheFactory缓存到SD卡上，
         *  File cacheDirectory = context.getExternalCacheDir();
         *  new File(cacheDirectory, diskCacheName);
         */
        builder.setDiskCache(
                new InternalCacheDiskCacheFactory(context, 300*1024*1024));
//        builder.setDiskCache(new ExternalCacheDiskCacheFactory(context,300*1024*1024));
//        builder.setDiskCache(new DiskLruCacheFactory("glide",300*1024*1024));

        /**
         *  内存缓存
         */
//        builder.setMemoryCache()
        /**
         * BitmapPool
         */
//        builder.setBitmapPool();

//        builder.setDecodeFormat(DecodeFormat.DEFAULT);

    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(MyDataModel.class, InputStream.class,
                new MyUrlLoader.Factory());
    }
}
