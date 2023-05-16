package com.example.photo_app.model.call.flickr;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PhotosetsResponse implements Serializable {
    @SerializedName("response")
    private List<PhotosetResponse> response;

    public PhotosetsResponse(List<PhotosetResponse> response) {
        this.response = response;
    }

    public List<PhotosetResponse> getResponse() {
        return response;
    }

    public class PhotosetResponse implements Serializable {
        private String Id;
        private String Primary;
        private String Secret;
        private String Server;
        private String Farm;
        private int Photos;
        private int Videos;
        private boolean NeedsInterstitial;
        private boolean VisCanSeeSet;
        private int CountViews;
        private int CountComments;
        private boolean CanComment;
        private int DateCreate;
        private int DateUpdate;
        private String Title;
        private String Description;
        private String Url;
        private String Owner;

        public PhotosetResponse(String id, String primary, String secret, String server, String farm, int photos, int videos, boolean needsInterstitial, boolean visCanSeeSet, int countViews, int countComments, boolean canComment, int dateCreate, int dateUpdate, String title, String description, String url, String owner) {
            Id = id;
            Primary = primary;
            Secret = secret;
            Server = server;
            Farm = farm;
            Photos = photos;
            Videos = videos;
            NeedsInterstitial = needsInterstitial;
            VisCanSeeSet = visCanSeeSet;
            CountViews = countViews;
            CountComments = countComments;
            CanComment = canComment;
            DateCreate = dateCreate;
            DateUpdate = dateUpdate;
            Title = title;
            Description = description;
            Url = url;
            Owner = owner;
        }

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public String getPrimary() {
            return Primary;
        }

        public void setPrimary(String primary) {
            Primary = primary;
        }

        public String getSecret() {
            return Secret;
        }

        public void setSecret(String secret) {
            Secret = secret;
        }

        public String getServer() {
            return Server;
        }

        public void setServer(String server) {
            Server = server;
        }

        public String getFarm() {
            return Farm;
        }

        public void setFarm(String farm) {
            Farm = farm;
        }

        public int getPhotos() {
            return Photos;
        }

        public void setPhotos(int photos) {
            Photos = photos;
        }

        public int getVideos() {
            return Videos;
        }

        public void setVideos(int videos) {
            Videos = videos;
        }

        public boolean isNeedsInterstitial() {
            return NeedsInterstitial;
        }

        public void setNeedsInterstitial(boolean needsInterstitial) {
            NeedsInterstitial = needsInterstitial;
        }

        public boolean isVisCanSeeSet() {
            return VisCanSeeSet;
        }

        public void setVisCanSeeSet(boolean visCanSeeSet) {
            VisCanSeeSet = visCanSeeSet;
        }

        public int getCountViews() {
            return CountViews;
        }

        public void setCountViews(int countViews) {
            CountViews = countViews;
        }

        public int getCountComments() {
            return CountComments;
        }

        public void setCountComments(int countComments) {
            CountComments = countComments;
        }

        public boolean isCanComment() {
            return CanComment;
        }

        public void setCanComment(boolean canComment) {
            CanComment = canComment;
        }

        public int getDateCreate() {
            return DateCreate;
        }

        public void setDateCreate(int dateCreate) {
            DateCreate = dateCreate;
        }

        public int getDateUpdate() {
            return DateUpdate;
        }

        public void setDateUpdate(int dateUpdate) {
            DateUpdate = dateUpdate;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }

        public String getOwner() {
            return Owner;
        }

        public void setOwner(String owner) {
            Owner = owner;
        }
    }

}