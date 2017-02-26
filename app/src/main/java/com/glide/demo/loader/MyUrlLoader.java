package com.glide.demo.loader;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

public class MyUrlLoader extends BaseGlideUrlLoader<MyDataModel> {
    public MyUrlLoader(Context context) {
        super(context);
    }

    @Override
    protected String getUrl(MyDataModel model, int width, int height) {
         //构建自己的url ，比如http://www.baidu.com/img/xx_1920x1080.jpg
        return model.buildUrl(width, height);
    }

    public static class Factory implements ModelLoaderFactory {

        @Override
        public ModelLoader build(Context context, GenericLoaderFactory factories) {
            return new MyUrlLoader(context);
        }

        @Override
        public void teardown() {

        }
    }
}