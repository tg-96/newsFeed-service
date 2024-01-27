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

    private String likeUsersEmail;

    public PostLikes(String likeUsersEmail) {
        this.likeUsersEmail = likeUsersEmail;
    }
}
