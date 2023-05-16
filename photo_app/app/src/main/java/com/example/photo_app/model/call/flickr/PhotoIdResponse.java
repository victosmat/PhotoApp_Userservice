package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoIdResponse {
    @SerializedName("response")
    private List<photoId> response;

    public PhotoIdResponse(List<photoId> response) {
        this.response = response;
    }

    public List<photoId> getResponse() {
        return response;
    }

    public void setResponse(List<photoId> response) {
        this.response = response;
    }

    public static class photoId {
        @SerializedName("id")
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
