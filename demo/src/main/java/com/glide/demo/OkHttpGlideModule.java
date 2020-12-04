package com.glide.demo;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.glide.demo.http.RestClient;

import java.io.InputStream;

import okhttp3.OkHttpClient;

/**
 * AppGlideModule的子类不会被混淆，由Glide加载发现
 * Glide会自动生成GeneratedAppGlideModuleImpl  build/generated/ap_generated_sources/out 目录
 */
@GlideModule
public class OkHttpGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        OkHttpClient client = RestClient.getImgDownloadClient();
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(client)); //https支持
    }
}