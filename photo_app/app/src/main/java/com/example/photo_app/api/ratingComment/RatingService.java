package com.example.photo_app.api.ratingComment;

import com.example.photo_app.model.ratingComment.Comment;
import com.example.photo_app.model.ratingComment.CommentDTO;
import com.example.photo_app.model.ratingComment.MessageResponse;
import com.example.photo_app.model.ratingComment.Rating;
import com.example.photo_app.model.ratingComment.RatingDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RatingService {

    @POST("rating-comment/rating/to-user/{id}")
    Call<MessageResponse<Rating>> create(@Body RatingDTO rating, @Path("id") String id);

    @GET("rating-comment/rating/photo/{id}")
    Call<MessageResponse<List<Rating>>> getAllByPhoto(@Path("id") String id);

    @PUT("rating-comment/rating")
    Call<MessageResponse<Rating>> update(@Body RatingDTO rating);

    @DELETE("rating-comment/rating/{id}")
    Call<MessageResponse<String>> deleteById(@Path("id") String id);
}
