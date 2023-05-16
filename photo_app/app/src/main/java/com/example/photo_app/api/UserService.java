package com.example.photo_app.api;

import com.example.photo_app.model.User;
import com.example.photo_app.model.call.LoginRequest;
import com.example.photo_app.model.call.LoginResponse;
import com.example.photo_app.model.call.Message;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {

    UserService authService = ApiClient.createService(UserService.class, null);

    @POST("auth/login")
    Call<LoginResponse> checkLogin(@Body LoginRequest loginRequest);

    @POST("auth/login/facebook")
    Call<LoginResponse> checkLoginFB(@Body Map<String,String> accessToken);

    @POST("auth/register")
    Call<Message> checkRegister(@Body User User);

    @GET("user/getUserFromJWT")
    Call<User> getUserFromJWT();

    @GET("user/getUsersByFollowing")
    Call<List<User>> getUsersByFollowing();

    @GET("user/getUsersByFollowed")
    Call<List<User>> getUsersByFollowed();

    @PUT("user/updateUser")
    Call<Message> updateUser(@Body User user);

    @GET("user/getUsersByKeyword/{keyword}")
    Call<List<User>> getUsersByKeyword(@Path("keyword") String keyword);

    @POST("user/follow/{id}")
    Call<Message> follow(@Path("id") Long id);

    @POST("user/unfollow/{id}")
    Call<Message> unfollow(@Path("id") Long id);

    @GET("user/getFollowing/{id}")
    Call<List<User>> getFollowing(@Path("id") Long id);

    @GET("user/getFollowed/{id}")
    Call<List<User>> getFollowed(@Path("id") Long id);

    @GET("user/checkFollow/{id}")
    Call<Message> checkFollow(@Path("id") Long id);

    @GET("user/getUserById/{id}")
    Call<User> getUserById(@Path("id") Long id);

    @POST("auth/login/google/{idToken}")
    Call<LoginResponse> checkLoginGoogle(@Path("idToken") String idToken);
}
