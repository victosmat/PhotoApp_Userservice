package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PhotoSourceResponse {
    @SerializedName("Response")
    private List<PhotoSource> photoSources;

    public PhotoSourceResponse(List<PhotoSource> photoSources) {
        this.photoSources = photoSources;
    }

    public List<PhotoSource> getPhotoSources() {
        return photoSources;
    }

    public class PhotoSource {
        @SerializedName("Label")
        private String label;
        @SerializedName("Width")
        private String width;
        @SerializedName("Height")
        private String height;
        @SerializedName("Source")
        private String source;
        @SerializedName("Url")
        private String url;
        @SerializedName("Media")
        private String media;

        public PhotoSource(String label, String width, String height, String source, String url, String media) {
            this.label = label;
            this.width = width;
            this.height = height;
            this.source = source;
            this.url = url;
            this.media = media;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMedia() {
            return media;
        }

        public void setMedia(String media) {
            this.media = media;
        }
    }
}
