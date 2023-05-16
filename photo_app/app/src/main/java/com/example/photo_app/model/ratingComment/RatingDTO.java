package com.example.photo_app.model.ratingComment;

public class RatingDTO {
    private Long id;
    private int rating;
    private String message;
    private Long userId;
    private String photoId;

    public RatingDTO() {
    }

    public RatingDTO(int rating, String message, Long userId, String photoId) {
        this.rating = rating;
        this.message = message;
        this.userId = userId;
        this.photoId = photoId;
    }

    public RatingDTO(Long id, int rating, String message, Long userId, String photoId) {
        this.id = id;
        this.rating = rating;
        this.message = message;
        this.userId = userId;
        this.photoId = photoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
}
