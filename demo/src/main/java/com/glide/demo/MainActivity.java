package com.glide.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.glide.demo.transform.BlurTransformation;
import com.glide.demo.transform.RadiusTransformation;
import com.glide.demo.util.Utils;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private ImageView line1_iv1, line1_iv2, line1_iv3, line1_iv4;
    private ImageView line2_iv1, line2_iv2, line2_iv3,line2_iv4;
    private ImageView line3_iv1, line3_iv2, line3_iv3;
    private ImageView line4_iv1, line4_iv2, line4_iv3;
    private ImageView line5_iv1, line5_iv2, line5_iv3;
    private ImageView line6_iv1, line6_iv2, line6_iv3;

    private String URL1 = "http://pic1.nipic.com/2008-12-30/200812308231244_2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        line1_iv1 = findViewById(R.id.line1_iv1);
        line1_iv2 = findViewById(R.id.line1_iv2);
        line1_iv3 = findViewById(R.id.line1_iv3);
        line1_iv4 = findViewById(R.id.line1_iv4);

        line2_iv1 = findViewById(R.id.line2_iv1);
        line2_iv2 = findViewById(R.id.line2_iv2);
        line2_iv3 = findViewById(R.id.line2_iv3);
        line2_iv4 = findViewById(R.id.line2_iv4);

        line3_iv1 = findViewById(R.id.line3_iv1);
        line3_iv2 = findViewById(R.id.line3_iv2);
        line3_iv3 = findViewById(R.id.line3_iv3);

        line4_iv1 = findViewById(R.id.line4_iv1);
        line4_iv2 = findViewById(R.id.line4_iv2);
        line4_iv3 = findViewById(R.id.line4_iv3);

        line5_iv1 = findViewById(R.id.line5_iv1);
        line5_iv2 = findViewById(R.id.line5_iv2);
        line5_iv3 = findViewById(R.id.line5_iv3);

        line6_iv1 = findViewById(R.id.line6_iv1);
        line6_iv2 = findViewById(R.id.line6_iv2);
        line6_iv3 = findViewById(R.id.line6_iv3);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                line1Test();
                line2Test();
                line3Test();
                line4Test();
                line5Test();
                line6Test();
            }
        });
//        line1Test();
//        line2Test();
//        line3Test();
//        line4Test();
//        line5Test();
//        line6Test();



        //圆角图片
        Drawable radiusDrawable = getRadiusDrawable(this, R.mipmap.quan, 4);
        ImageView imageView = findViewById(R.id.imageview2);
        imageView.setImageDrawable(radiusDrawable);

    }

    private void line1Test() {
        GlideApp.with(this).load(URL1).into(line1_iv1);
        GlideApp.with(this).load(URL1).fitCenter().into(line1_iv2);
        GlideApp
                .with(this)
                .load(URL1)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(line1_iv3);

        //通过RequestOptions封装请求参数
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher);
        GlideApp
                .with(this)
                .load(URL1)
                .apply(requestOptions)
                .into(line1_iv3);
    }

    /*
      图片变换
      Glide从加载了原始图片到最终展示给用户之前，又进行了一些变换处理，从而能够实现一些更加丰富的图片效果，
      如图片圆角化、圆形化、模糊化等等。

      至于具体要进行什么样的图片变换操作，这个通常都是需要我们自己来写的。不过Glide已经内置了几种图片变换操作，
      我们可以直接拿来使用，比如CenterCrop、FitCenter、CircleCrop等。
      但所有的内置图片变换操作其实都不需要使用transform()方法，Glide为了方便我们使用直接提供了现成的API：
        RequestOptions options = new RequestOptions()
                .centerCrop();
        RequestOptions options = new RequestOptions()
                .fitCenter();
        RequestOptions options = new RequestOptions()
                .circleCrop();

    glide-transformations的项目主页地址是 https://github.com/wasabeef/glide-transformations 。
    dependencies {
         implementation 'jp.wasabeef:glide-transformations:3.0.1'
      }

      我们可以对图片进行单个变换处理，也可以将多种图片变换叠加在一起使用。比如我想同时对图片进行模糊化和黑白化处理，就可以这么写：
       RequestOptions options = new RequestOptions()
        .transforms(new BlurTransformation(), new GrayscaleTransformation()); //同时对图片进行模糊化和黑白化处理
     */
    private void line2Test() {

        //圆角
        GlideApp
                .with(this)
                .load(URL1)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new RadiusTransformation(this, 10))
                .into(line2_iv1);

        //圆形
        GlideApp
                .with(this)
                .load(URL1)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
//                .transform(new CircleTransformation()) //自定义
                .circleCrop() //Glide内置
                .into(line2_iv2);

        //高斯模糊
        GlideApp
                .with(this)
                .load(URL1)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .transform(new BlurTransformation(this, 20))
                .into(line2_iv3);


        //测试请求失败，占位图片是否圆角, 结论占位图片是不会转换为圆角的
        GlideApp
                .with(this)
                .load("http://www.baidu.com/sxx")
                .centerCrop()
                .placeholder(R.mipmap.timg)
                .error(R.mipmap.timg)
                .transform(new RadiusTransformation(this, 10))
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        System.out.println("onLoadFailed "+e);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                       System.out.println("onResourceReady");
                        return false;
                    }
                })
                .into(line2_iv4);
    }

    /**
     * 缓存策略
     * 默认情况下，Glide自动就是开启内存缓存的,
     */
    private void line3Test() {

        /**
         * 硬盘缓存策略：
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         *
         * 上面五种参数的解释本身并没有什么难理解的地方，但是关于转换过后的图片这个概念大家可能需要了解一下。
         * 就是当我们使用Glide去加载一张图片的时候， Glide默认并不会将原始图片展示出来，而是会对图片进行压缩和转换。
         */
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE); //不使用硬盘缓存
        Glide.with(this)
                .load(URL1)
                .apply(options)
                .into(line3_iv1);


        options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .skipMemoryCache(true) //不适用内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE); //不使用硬盘缓存
        Glide.with(this)
                .load(URL1)
                .apply(options)
                .into(line3_iv2);

    }

    //加载图片大小
    private void line4Test() {
        /**
         * 实际上，使用Glide在大多数情况下我们都是不需要指定图片大小的，
         * 因为Glide会自动根据ImageView的大小来决定图片的大小，以此保证图片不会占用过多的内存从而引发OOM。
         * 必须给图片指定一个固定的大小 .override(width,height)
         */
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .override(50, 50) //固定大小，也就是说，Glide现在只会将图片加载成50*50像素的尺寸，而不会管你的ImageView的大小是多少了。
                .diskCacheStrategy(DiskCacheStrategy.NONE); //不使用缓存
        Glide.with(this)
                .load(URL1)
                .apply(options)
                .into(line4_iv1);

        options = new RequestOptions()
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .centerCrop()
                .override(Target.SIZE_ORIGINAL) //加载一张图片的原始尺寸，这样的话，Glide就不会再去自动压缩图片，而是会去加载图片的原始尺寸。当然，这种写法也会面临着更高的OOM风险。
                .diskCacheStrategy(DiskCacheStrategy.NONE); //不使用缓存
        Glide.with(this)
                .load(URL1)
                .apply(options)
                .into(line4_iv2);
    }

    //指定加载格式
    public void line5Test() {
        //使用Glide加载GIF图并不需要编写什么额外的代码，Glide内部会自动判断图片格式。
        Glide.with(this)
                .load("http://guolin.tech/test.gif")
                .into(line5_iv1);


        //指定加载格式,在Glide 3中的语法是先load()再asBitmap()的，而在Glide 4中是先asBitmap()再load()的。
        Glide.with(this)
                .asBitmap() //只允许加载静态图片，不需要Glide去帮我们自动进行图片格式的判断了。如果你传入的还是一张GIF图的话，Glide会展示这张GIF图的第一帧，而不会去播放它。
                .load("http://guolin.tech/test.gif")
                .into(line5_iv2);

        /*
        强制指定加载动态图片，对应的方法是asGif()。而Glide 4中又新增了asFile()方法和asDrawable()方法，
        分别用于强制指定文件格式的加载和Drawable格式的加载，用法都比较简单，就不再进行演示了。
         */
        Glide.with(this)
                .asGif()
                .load("http://guolin.tech/test.gif")
                .into(line5_iv2);
    }

    //回调与监听
    public void line6Test() {
        /**
         * Glide的into()方法中是可以传入ImageView的。那么into()方法还可以传入别的参数吗？
         * 我们可以让Glide加载出来的图片不显示到ImageView上吗？答案是肯定的，这就需要用到自定义Target功能。
         */
        SimpleTarget<Drawable> simpleTarget = new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                line6_iv1.setImageDrawable(resource);
            }
        };

        Glide.with(this)
                .load("http://guolin.tech/book.png")
                .into(simpleTarget);


        /**
         * preload()方法
         * Glide加载图片虽说非常智能，它会自动判断该图片是否已经有缓存了，如果有的话就直接从缓存中读取，
         * 没有的话再从网络去下载。但是如果我希望提前对图片进行一个预加载，等真正需要加载图片的时候就直接从缓存中读取，
         * 不想再等待慢长的网络加载时间了。就可以使用preload()
         * preload()方法有两个方法重载，一个不带参数，表示将会加载图片的原始尺寸，另一个可以通过参数指定加载图片的宽和高。
         */

        Glide.with(this)
                .load("http://guolin.tech/book.png")
                .preload();
        //调用了预加载之后，我们以后想再去加载这张图片就会非常快了，因为Glide会直接从缓存当中去读取图片并显示出来
        Glide.with(this)
                .load("http://guolin.tech/book.png")
                .into(line6_iv2);

        /**
         * submit
         * 这个方法只会下载图片，而不会对图片进行加载。当图片下载完成之后，我们可以得到图片的存储路径，以便后续进行操作。
         * submit()方法是用于下载原始尺寸的图片，而submit(int width, int height)则可以指定下载图片的尺寸。
         * 当调用了submit()方法后会立即返回一个FutureTarget对象，然后Glide会在后台开始下载图片文件。接下来我们调用FutureTarget的get()方法就可以去获取下载好的图片文件了，如果此时图片还没有下载完，那么get()方法就会阻塞住，一直等到图片下载完成才会有值返回。
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String url = "http://www.guolin.tech/book.png";
                    final Context context = getApplicationContext();
                    FutureTarget<File> target = Glide.with(context)
                            .asFile() //指定加载格式
                            .load(url)
                            .submit();
                    final File imageFile = target.get(); //方法阻塞，知道图片下载完成
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        /**
         *  listener()方法
         *  用来监听Glide加载图片的状态。
         *  不同于刚才几个方法都是要替换into()方法的，listener()是结合into()方法一起使用的，当然也可以结合preload()方法一起使用
         */

        Glide.with(this)
                .load("http://www.guolin.tech/book.png")
                .listener(new RequestListener<Drawable>() {
                    //当图片加载失败的时候就会回调onLoadFailed()方法
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//返回false就表示这个事件没有被处理，还会继续向下传递，返回true就表示这个事件已经被处理掉了，从而不会再继续向下传递。举个简单点的例子，如果我们在RequestListener的onResourceReady()方法中返回了true，那么就不会再回调Target的onResourceReady()方法了。
                        return false;
                    }

                    //当图片加载完成的时候就会回调onResourceReady()方法
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//返回false就表示这个事件没有被处理，还会继续向下传递，返回true就表示这个事件已经被处理掉了，从而不会再继续向下传递。举个简单点的例子，如果我们在RequestListener的onResourceReady()方法中返回了true，那么就不会再回调Target的onResourceReady()方法了。
                        return false;
                    }
                })
                .into(line6_iv3);
    }


    private static Drawable getRadiusDrawable(Context ctx, @DrawableRes int imgRes, int radius) {
        radius = Utils.dp2px(ctx, radius);
        Bitmap toTransform = BitmapFactory.decodeResource(ctx.getResources(), imgRes);
        int width = toTransform.getWidth();
        int height = toTransform.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setHasAlpha(true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(new BitmapShader(toTransform, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        canvas.drawRoundRect(new RectF(0, 0, width, height), radius, radius, paint);
        return new BitmapDrawable(ctx.getResources(), bitmap);
    }


}
