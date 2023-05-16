package com.example.photo_app.model.ratingComment;

public class MessageResponse<T> {
    private Long client;
    private Integer status;
    private String message;
    private T data;

    public MessageResponse() {
    }

    public MessageResponse(Long client, Integer status, String message, T data) {
        this.client = client;
        this.status = status;
        this.message = message;
        this.data = data;
    }


    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getClient() {
        return client;
    }

    public void setClient(Long client) {
        this.client = client;
    }
}
