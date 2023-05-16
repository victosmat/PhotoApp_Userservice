package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PhotosByUserResponse {
    @SerializedName("photos")
    private List<photoByUserResponse> photos;

    public PhotosByUserResponse() {
        this.photos = new ArrayList<>();
    }
    public PhotosByUserResponse(List<photoByUserResponse> photos) {
        this.photos = photos;
    }

    public List<photoByUserResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<photoByUserResponse> photos) {
        this.photos = photos;
    }

    public static class photoByUserResponse {
        @SerializedName("id")
        private String id;
        @SerializedName("url")
        private String url;

        public photoByUserResponse(String id,String url) {
            this.id= id;
            this.url = url;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
