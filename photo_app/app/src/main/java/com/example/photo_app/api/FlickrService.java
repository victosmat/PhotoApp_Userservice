package com.example.photo_app.api;

import com.example.photo_app.model.call.flickr.PhotoByPhotosetResponse;
import com.example.photo_app.model.call.flickr.PhotoIdResponse;
import com.example.photo_app.model.call.flickr.PhotoSourceResponse;
import com.example.photo_app.model.call.flickr.PhotoURLResponse;
import com.example.photo_app.model.call.flickr.PhotosByUserResponse;
import com.example.photo_app.model.call.flickr.PhotosetsResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface FlickrService {
    @Multipart
    @POST("/flickr/upload_img")
    Call<PhotoIdResponse> uploadImages(@Part List<MultipartBody.Part> files);

    // trả đầy đủ
    @GET("/api/v1/photo/getById")
    Call<PhotoSourceResponse> getImageUrl(@Query("photo_id") String id);

    // trả duy 1 nhất 1 url string (size original)
    @GET("/api/v1/photo/getById")
    Call<PhotoURLResponse> getImageUrlByImgId(@Query("photo_id") String id);

    @GET("/api/v1/photo/getByUserId")
    Call<PhotosByUserResponse> getImageByUserId(@Query(("user_id")) String userId);

    @GET("/api/v1/photoset/getByUserid")
    Call<PhotosetsResponse> getPhotosetByUserId();

    @GET("/api/v1/photoset/getPhotoByPhotosetId")
    Call<PhotoByPhotosetResponse> getPhotoByPhotosetId(Integer photosetId);

}
