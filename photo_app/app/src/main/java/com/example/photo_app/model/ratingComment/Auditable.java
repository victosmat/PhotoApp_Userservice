package com.example.photo_app.model.ratingComment;

import java.io.Serializable;
import java.time.LocalDateTime;


public abstract class Auditable implements Serializable {

    protected LocalDateTime createdDate;
    protected LocalDateTime lastModifiedDate;

    public Auditable() {
    }

    public Auditable(LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    //    @Column(name = "status", length = 20)
//    protected String status;
}

