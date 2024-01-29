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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Comments comments;
    private String likeUsersEmail;

    public CommentLikes(Comments comments,String likeUsersEmail) {
        this.comments = comments;
        this.likeUsersEmail = likeUsersEmail;
    }
}
