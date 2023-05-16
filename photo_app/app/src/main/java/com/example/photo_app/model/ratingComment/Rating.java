package com.example.photo_app.model.ratingComment;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Rating implements Serializable {

    private long id;
    private String message;
    private int rating;
    private Long userId;
    private String username;
    private String photoId;
    private String lastUpdate;


    public Rating() {
    }

    public Rating(LocalDateTime createdDate, LocalDateTime lastModifiedDate, long id, String message, int rating, Long userId, String username, String photoId, String lastUpdate) {
//        super(createdDate, lastModifiedDate);
        this.id = id;
        this.message = message;
        this.rating = rating;
        this.userId = userId;
        this.username = username;
        this.photoId = photoId;
        this.lastUpdate = lastUpdate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
