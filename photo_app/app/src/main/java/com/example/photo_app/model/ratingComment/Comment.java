package com.example.photo_app.model.ratingComment;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Comment  implements Serializable {
    private long id;
    private String message;
    private Long userId;
    private String username;

    private String photoId;
    private Long postId;
    private String lastUpdate;

    public Comment() {
    }

    public Comment(LocalDateTime createdDate, LocalDateTime lastModifiedDate, long id, String message, Long userId, String username, String photoId, Long postId, String lastUpdate) {
//        super(createdDate, lastModifiedDate);
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.photoId = photoId;
        this.postId = postId;
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

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
