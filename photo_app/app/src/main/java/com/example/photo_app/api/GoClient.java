package com.example.photo_app.api;

import android.content.Context;

import java.net.CookieManager;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoClient {

    private static final String BASE_URL = "https://s4fqy76lklhts562eqmcjph56i.srv.us";
//    private static final String BASE_URL = "http://172.26.63.159:8900/";
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();
    public static <T> T createServiceNonCookie(Class<T> serviceClass, Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        ;
        // Nếu context khác null thì sử dụng HeaderInterceptor
        if (context != null) {
            httpClientBuilder.addInterceptor(new HeaderInterceptor(context));
//            SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
//            String token = prefs.getString("token", null);
//            Log.i("token ApiClient createService", token);
        }
        OkHttpClient httpClient = httpClientBuilder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient);
        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }


    public static <T> T createService(Class<T> serviceClass, Context context, CookieManager cookieManager) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder() .cookieJar(new JavaNetCookieJar(cookieManager))
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS);
        ;
        // Nếu context khác null thì sử dụng HeaderInterceptor
        if (context != null) {
            httpClientBuilder.addInterceptor(new HeaderInterceptor(context));
//            SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
//            String token = prefs.getString("token", null);
//            Log.i("token ApiClient createService", token);
        }
        OkHttpClient httpClient = httpClientBuilder.build();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient);
        Retrofit retrofit = retrofitBuilder.build();

        return retrofit.create(serviceClass);
    }
    public static String getBaseUrl(){
        return BASE_URL;
    }
}
