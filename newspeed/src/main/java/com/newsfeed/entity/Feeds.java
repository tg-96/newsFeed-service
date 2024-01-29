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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;
    @OneToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    public Feeds(Posts post,Member owner) {
        this.owner = owner;
        this.post = post;
    }
}
