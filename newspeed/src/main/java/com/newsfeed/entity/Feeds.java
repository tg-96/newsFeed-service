package com.newsfeed.entity;

import com.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Feeds extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "feeds_id")
    private Long id;
    private String text;
    private String writerEmail;
    private String image;

    public Feeds(String text, String writerEmail, String image, Member member) {
        this.text = text;
        this.writerEmail = writerEmail;
        this.image = image;
    }
}
