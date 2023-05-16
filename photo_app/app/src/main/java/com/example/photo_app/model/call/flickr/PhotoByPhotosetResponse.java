package com.example.photo_app.model.call.flickr;

import java.util.List;

public class PhotoByPhotosetResponse {
    private List<photoResponse> response;

    public PhotoByPhotosetResponse(List<photoResponse> response) {
        this.response = response;
    }

    public List<photoResponse> getResponse() {
        return response;
    }

    private class photoResponse {
        private String Id;
        private String Title;
//        private String Url;

        public photoResponse(String id, String title) {
            Id = id;
            Title = title;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }
    }
}
