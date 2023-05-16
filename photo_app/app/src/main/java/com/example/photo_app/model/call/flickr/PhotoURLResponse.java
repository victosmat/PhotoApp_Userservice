package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PhotoURLResponse implements Serializable {
    @SerializedName("url")
    private String url;
    @SerializedName("id")
    private String id;

    public PhotoURLResponse(String url, String id) {
        this.url = url;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PhotoURLResponse(){}

    public PhotoURLResponse(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
