package com.newsfeed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostLikes {
    @GeneratedValue
    @Id
    @Column(name = "postLike_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;
    private String likeUsersEmail;

    public PostLikes(Posts posts, String likeUsersEmail) {
        this.posts = posts;
        this.likeUsersEmail = likeUsersEmail;
    }
}
