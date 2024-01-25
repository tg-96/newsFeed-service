package com.newsfeed.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Member {
    @GeneratedValue
    @Id
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String image;
    private String introduction;
}
