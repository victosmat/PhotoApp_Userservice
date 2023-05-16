package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "follow")
@Data // lombok
public class Follow {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long followingUserId;

    @Column(nullable = false)
    private Long followedUserId;
}
