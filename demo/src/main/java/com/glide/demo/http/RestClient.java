package com.glide.demo.http;


import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

/**
 * OkHttp请求
 *
 * @author 张全
 */
public final class RestClient {
    private static final String TAG = "RestClient";
    public static final int TIMEOUT_CONNECTION = 20; //连接超时
    public static final int TIMEOUT_READ = 20; //读取超时
    public static final int TIMEOUT_WRITE = 20; //写入超时

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static OkHttpClient imgDownloadClient;


    /**
     * 图片下载
     *
     * @return
     */
    public static OkHttpClient getImgDownloadClient() {
        if (null == imgDownloadClient) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT_CONNECTION, TimeUnit.SECONDS)
                    .readTimeout(50, TimeUnit.SECONDS)
                    .writeTimeout(50, TimeUnit.SECONDS);
            getUnsafeOkHttpClient(builder);
            imgDownloadClient = builder.build();
        }
        return imgDownloadClient;
    }


    public static void getUnsafeOkHttpClient(OkHttpClient.Builder builder) {
        try {
            final X509TrustManager trustManager = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };

            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, new TrustManager[]{trustManager}, new SecureRandom());
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            builder.sslSocketFactory(sslSocketFactory, trustManager);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
