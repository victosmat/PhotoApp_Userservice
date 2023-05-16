package com.example.photo_app.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderInterceptor implements Interceptor {

    private Context context;

    public HeaderInterceptor(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);

        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();

        // Thêm token vào header
        if (token != null) {
            builder.header("Authorization", "Bearer " + token);
        }

        Request newRequest = builder.build();

        // In ra giá trị của headers
        Headers headers = newRequest.headers();
        for (int i = 0, size = headers.size(); i < size; i++) {
            String name = headers.name(i);
            String value = headers.value(i);
        }

        return chain.proceed(newRequest);
//        Request original = chain.request();
//        String token = getTokenFromSomeWhere();
//        Log.d("HeaderInterceptor", "Token: " + token);
//        if (token != null) {
//            Request.Builder requestBuilder = original.newBuilder()
//                    .header("Authorization", "Bearer " + token)
//                    .method(original.method(), original.body());
//            Request request = requestBuilder.build();
//            return chain.proceed(request);
//        }
//        return chain.proceed(original);
    }

    private String getTokenFromSomeWhere() {
        SharedPreferences prefs = context.getSharedPreferences("dataLogin", Context.MODE_PRIVATE);
        String token = prefs.getString("token", null);
//        Log.d("HeaderInterceptor", "Token: " + token);
        return token;
    }

}
