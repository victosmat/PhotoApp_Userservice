package com.example.photo_app.model.notification;


public class NotificationModel {
    private Long id;
    private Long ObjectReceiveId;
    private String type;
    private Long ObjectSendId;
    private String content;
    private String photoId;
    private String status;

    public NotificationModel(Long id, Long objectReceiveId, String type, Long objectSendId, String content, String photoId, String status) {
        this.id = id;
        ObjectReceiveId = objectReceiveId;
        this.type = type;
        ObjectSendId = objectSendId;
        this.content = content;
        this.photoId = photoId;
        this.status = status;
    }

    public NotificationModel() {
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getObjectReceiveId() {
        return ObjectReceiveId;
    }

    public void setObjectReceiveId(Long objectReceiveId) {
        ObjectReceiveId = objectReceiveId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getObjectSendId() {
        return ObjectSendId;
    }

    public void setObjectSendId(Long objectSendId) {
        ObjectSendId = objectSendId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    //    public enum  Type {
//        COMMENT, RATING, FOLLOW
//    }
//
//    public enum  Status {
//        SEEN, UNSEEN
//    }
}