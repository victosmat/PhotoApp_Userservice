package com.example.photo_app.api;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

//    private static final String BASE_URL = "https://jsqedvglu56wklwlbxfhvc2ude.srv.us/api/";
    private static final String BASE_URL = "https://4x6dhdewqcxwk32ykeseerrmda.srv.us/api/";

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    public static <T> T createService(Class<T> serviceClass, Context context) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
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
}
