package com.example.photo_app.model.ratingComment;

public class CommentDTO {
    private Long id;
    private String message;
    private Long userId;
    private String photoId;
    private Long postId;

    public CommentDTO(Long id, String message, Long userId, String photoId, Long postId) {
        this.id = id;
        this.message = message;
        this.userId = userId;
        this.photoId = photoId;
        this.postId = postId;
    }

    public CommentDTO() {
    }

    public CommentDTO(String message, Long userId, String photoId, Long postId) {
        this.message = message;
        this.userId = userId;
        this.photoId = photoId;
        this.postId = postId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
