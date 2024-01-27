package com.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentLikes {
    @Id
    @GeneratedValue
    @Column(name = "commentLike_id")
    private Long id;
    private String likeUsersEmail;

    public CommentLikes(String likeUsersEmail) {
        this.likeUsersEmail = likeUsersEmail;
    }
}
