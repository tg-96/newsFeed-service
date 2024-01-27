package com.newsfeed.entity;

import com.newsfeed.common.ActivityType;
import com.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Activity extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "activity_id")
    private Long id;
    private ActivityType type;
    private String text;
    private String writerEmail;
    public Activity(ActivityType type, String text, String writerEmail, Member member) {
        this.type = type;
        this.text = text;
        this.writerEmail = writerEmail;
    }
}
