package com.glide.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.target.ViewTarget;
import com.glide.demo.loader.MyDataModel;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * http://www.jianshu.com/p/c9efd313e79e
 */
public class MainActivity extends Activity implements View.OnClickListener {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        imageView = (ImageView) findViewById(R.id.imageview);

        //   /data/user/0/com.glide.demo/cache/image_manager_disk_cache
        File photoCacheDir = Glide.getPhotoCacheDir(this);
        System.out.println(photoCacheDir);


    }

    String[] imgUrls = new String[]{
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487959361519&di=cf295967688db077308de56f548f9ad0&imgtype=0&src=http%3A%2F%2Fimage.tianjimedia.com%2FuploadImages%2F2015%2F038%2F18%2F4A4T424232HW.jpg",
            "https://ss1.baidu.com/9vo3dSag_xI4khGko9WTAnF6hhy/image/h%3D220/sign=f6e284fb71310a55db24d9f687444387/503d269759ee3d6d542f99c34b166d224e4adec8.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487959695970&di=f6b9c2157fc4434c15d74501aa192949&imgtype=jpg&src=http%3A%2F%2Fa.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F962bd40735fae6cdcb6eb56907b30f2443a70fdb.jpg",
            "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1487959708406&di=b5fdbb53b43a2dab67e5a696e5849b49&imgtype=jpg&src=http%3A%2F%2Fb.hiphotos.baidu.com%2Fimage%2Fpic%2Fitem%2F0d338744ebf81a4c48e7f2c1df2a6059242da6e4.jpg"
    };
    String gifUrl = "http://img5.imgtn.bdimg.com/it/u=2220377401,4054930196&fm=23&gp=0.jpg";

    @Override
    public void onClick(View v) {

//        Glide.with(this).load(imgUrls[0]).into(imageView); //网络图片
//        Glide.with(this).load(file).into(imageView); //file 加载本地图片、视频等
//        Glide.with(this).load(R.mipmap.ic_launcher).into(imageView); //资源id
//        Glide.with(this).load(Uri.parse(imgUrls[0])).into(imageView); //uri
//        Glide.with(this).load(gifUrl).into(imageView); //gif图片

//        base();
//         loadGifAsBitmap();
//        resize();
//        thumbnail();
//            target();
//        buildUrl();
        downloadOnly();
//        downloadOnly2();
    }

    private void base() {
        String url = imgUrls[1];
        Glide.with(this)
                .load(url)
                //
                .placeholder(R.mipmap.ic_launcher) //占位符
                .error(R.mipmap.error) //加载失败
                .fallback(R.mipmap.empty) // 当load的url为null，首先回调fallback，如果没有设置fallback()则回调error()
                //-------裁剪图片
                .centerCrop()
//                .fitCenter()
                //-------动画
                .crossFade(2000) //渐显效果
//                .animate(animator) //自定义
//                 .dontAnimate() //不使用动画
                //-------监听器
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        System.err.println("model=" + model);
                        System.err.println("target=" + target);
                        System.err.println("isFirstResource=" + isFirstResource);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        System.out.println("===========onResourceReady=========");
                        System.out.println("resource=" + resource);
                        System.out.println("model=" + model);
                        System.out.println("target=" + target);
                        System.out.println("isFromMemoryCache=" + isFromMemoryCache);
                        System.out.println("isFirstResource=" + isFirstResource);
                        return false;
                    }
                })
                .into(imageView);
    }

    /**
     * 将gif作为bitmap加载，此时GIF就不会动画了
     */
    private void loadGifAsBitmap() {
        Glide.with(this)
                .load(gifUrl)
                .asBitmap() //用bitMap播放Gif  此时gif就不是动图了
                .placeholder(R.mipmap.ic_launcher) //占位符
                .error(R.mipmap.error) //加载失败
                .into(imageView);
    }

    /**
     * 调整图片大小 override(int,int)
     * 单位是像素，裁剪你的图片大小。其实Glide已经会自动根据你mageView裁剪照片来放在缓存中了。
     * 但是不想适应ImageView大小的时候，可以调用这个方法.override()。
     */
    private void resize() {
        Glide.with(this)
                .load(imgUrls[1])
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error)
                .override(600, 600)
                .into(imageView);
    }

    /**
     * 缩略图
     * 缩略图相当于一个动态的placeHolder
     * 先显示缩略图，然后显示大图
     */
    private void thumbnail() {
//        Glide.with(this)
//                .load(imgUrls[1])
//                .thumbnail(0.1f)//表示为原图的十分之一
//                .into(imageView);

        /**
         *    当缩略图也需要通过网络加载全部解析度的时候。
         */
        //网络图片作为缩略图
        DrawableTypeRequest<String> thumbanilImg = Glide.with(this)
                .load(imgUrls[1]);
        Glide.with(this)
                .load(imgUrls[2])
                .thumbnail(thumbanilImg)
                .into(imageView);


    }

    /**
     * 关于缓存
     * Glide用了内存缓存和‘本地缓存机制’,Glide会自动缓存到内存
     * skipMemoryCache( true ) 不缓存到内存中
     * diskCacheStrategy( DiskCacheStrategy.NONE ) 不缓存到本地
     * <p>
     * 本地缓存机制
     * Glide默认会缓存Image的很多个版本，比如原图，如果你的imageView大小的缓存。.diskCacheStrategy(  )有以下几种缓存策略：
     * DiskCacheStrategy.NONE 什么都不缓存
     * DiskCacheStrategy.SOURCE 只缓存最高解析图的image
     * DiskCacheStrategy.RESULT 缓存最后一次那个image,比如有可能你对image做了转化
     * DiskCacheStrategy.ALL image的所有版本都会缓存
     * <p>
     * 设置内存级缓存策列
     * Glide.get(this).setMemoryCategory();
     */

    private void cache() {
        Glide.with(this)
                .load(imgUrls[1])
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error)
//                .skipMemoryCache(false)
//                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);

    }

    /**
     * 请求优先级
     * 加载图片肯定也是有先后顺序，Glide提供了.priority()这个方法，它接收以下几个参数：
     * Priority.LOW
     * Priority.NORMAL
     * Priority.HIGH
     * Priority.IMMEDIATE
     * 但是Glide并不一定会按照你的顺序来，只是尽量按照你的顺序来。（比如给一张很大的图片最高的优先权，但是它并不一定比低优先级的图先加载出来，这个时候只有使用缩略图了）
     */
    private void priority() {
        Glide.with(this)
                .load(imgUrls[1])
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.error)
                .priority(Priority.HIGH) //设置优先级
                .into(imageView);
    }

    private void target() {
        Glide.with(this)
                .load("")
                .into(new ViewTarget<ImageView, GlideDrawable>(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                        ImageView imageView = this.view;
                        imageView.setImageDrawable(resource);
                        // Set your resource on myView and/or start your animation here.
                    }

                    @Override
                    public void onStart() {
                        super.onStart();
                    }

                    @Override
                    public void onStop() {
                        super.onStop();
                    }

                    @Override
                    public void onDestroy() {
                        super.onDestroy();
                    }

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                    }
                });
        //加载bitmap
        Glide.with(this)
                .load("")
                .asBitmap()
                .into(new ViewTarget<ImageView, Bitmap>(imageView) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation anim) {
                        ImageView imageView = this.view;
                        imageView.setImageBitmap(resource);
                        // Set your resource on myView and/or start your animation here.
                    }
                });
        //加载Gif
        Glide.with(this)
                .load("")
                .asGif()
                .into(new ViewTarget<ImageView, GifDrawable>(imageView) {
                    @Override
                    public void onResourceReady(GifDrawable resource, GlideAnimation anim) {
                        ImageView imageView = this.view;
                        imageView.setImageDrawable(resource);
                        // Set your resource on myView and/or start your animation here.
                    }
                });

        Glide.with(this)
                .load("")
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        //在这里执行
                        return false;
                    }
                })
                .into(new ViewTarget<ImageView, GlideDrawable>(imageView) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation anim) {
                        ImageView imageView = this.view;
                        imageView.setImageDrawable(resource);
                        // Set your resource on myView and/or start your animation here.
                    }
                });
    }

    //-------------------------------------




    private void buildUrl() {
        MyDataModel dataModel = new MyDataModel() {

            @Override
            public String buildUrl(int width, int height) {
                return imgUrls[3];
            }
        };
        //每个请求动态配置Loader
//        Glide.with(this)
//                .using(new MyUrlLoader(this)) //设置Loader
//                .load(dataModel)
//                .into(imageView);
        //将Loader注册到GlideModule中，就不用每次都设置了
        Glide.with(this)
                .load(dataModel)
                .into(imageView);
    }

    /**
     * 下载图片
     */
    private void downloadOnly(){
        //1、downloadOnly(width,height)
        System.out.println("00000000000000000000000");
       final FutureTarget<File> future = Glide.with(getApplicationContext())
                .load(imgUrls[1])
                .downloadOnly(500, 500);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File cacheFile = future.get();//这个方法必须在后台线程中执行
                    System.out.println("downloadOnly(width,height)....cacheFile="+cacheFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("111111111111111111111111");

        //2、downloadOnly(target) 异步下载,回调到主线程中
        Glide.with(getApplicationContext())
                .load(imgUrls[2])
                .downloadOnly(new SimpleTarget<File>() {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        Bitmap bitmap = BitmapFactory.decodeFile(resource.getAbsolutePath());
                        System.out.println("downloadOnly（target），bitmap="+bitmap+",thread="+Thread.currentThread().getName());
                    }
                });

        System.out.println("222222222222222222222222");

        //3、into(width，height)  注意：get不能在主线程中调用,会阻塞主线程
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Bitmap bitmap = Glide.with(getApplicationContext())
                                .load(imgUrls[3])
                                .asBitmap()
                                .centerCrop()
                                .into(500, 500)
                                .get(); //阻塞线程，不能在主线程中使用

                        System.out.println("into(width,height).....bitmap=" + bitmap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }

            }).start();
        System.out.println("333333333333333333333333");

    }

    private void downloadOnly2(){
        //1、downloadOnly(width,height) 同步下载
//        FutureTarget<File> future = Glide.with(getApplicationContext())
//                .load(imgUrls[1])
//                .downloadOnly(500, 500);
        System.out.println("111111111111111111111111");
        System.out.println("222222222222222222222222");
        //3、into(width，height)  注意：不能在主线程中调用
//          Glide.with(getApplicationContext())
//                .load(imgUrls[3])
//                .asBitmap()
//                .centerCrop()
//                .into(500, 500);
        System.out.println("33333333333333");

        //关闭网络  测试缓存
        Glide.with(this)
                .load(imgUrls[3])
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

}
