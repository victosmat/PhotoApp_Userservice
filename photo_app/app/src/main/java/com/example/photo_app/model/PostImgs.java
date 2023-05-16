package com.example.photo_app.model;

public class PostImgs {
    private int id;
    private int post_id;
    private String image_id;

    public PostImgs(int id, int post_id, String image_id) {
        this.id = id;
        this.post_id = post_id;
        this.image_id = image_id;
    }
    public PostImgs () {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPost_id() {
        return post_id;
    }

    public void setPost_id(int post_id) {
        this.post_id = post_id;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }
    public String toString() {
        return "PostImgs{" +
                "id=" + id +
                ", post_id=" + post_id +
                ", img_id=" + image_id +
                '}';
    }
}
