package com.example.photo_app.model;

import com.example.photo_app.model.call.flickr.PhotoURLResponse;

import java.util.ArrayList;

public class Post {
    private int id;
    private String caption;
    private String created_at;
    private int user_id;

    private ArrayList<PhotoURLResponse> imageUrls;

    public Post(int id, String caption, String created_at, int user_id) {
        this.id = id;
        this.caption = caption;
        this.created_at = created_at;
        this.user_id = user_id;
    }

    public Post() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String toString(){
        return "id: " + id + " caption: " + caption + " created_at: " + created_at + " user_id: " + user_id;
    }

    public ArrayList<PhotoURLResponse> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<PhotoURLResponse> imageUrls) {
        this.imageUrls = imageUrls;
    }
}
