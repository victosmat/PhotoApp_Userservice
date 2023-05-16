package com.example.photo_app.api;

import com.example.photo_app.model.Post;
import com.example.photo_app.model.PostImgs;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PostService {
    @GET("get_feed")
    Call<ArrayList<Post>> getFeed();

    @GET("get_posts_by_keyword")
    Call<ArrayList<Post>> getPostsByKeyword(@Query("keyword") String keyword);


    @GET("get_post_imgs/{post_id}")
    Call<ArrayList<PostImgs>> getPostImgs(@Path("post_id") int post_id);

    @POST("upload")
    Call<Post>  uploadPost(@Body Map<String, Object> body);

    @GET("get_list_urlImage/{user_id}")
    Call<ArrayList<String>> getListUrlImage(@Path("user_id") int user_id);
    @GET("get_image_owner/{photo_id}")
    Call<Map<String,Long>> getImageOwner(@Path("photo_id") String photo_id);
}
