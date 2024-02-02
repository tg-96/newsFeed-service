package com.preOrderService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class CommentLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commentLike_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comments;
    private Long likeMemberId;

    public CommentLikes(Comments comments,Long likeMemberId) {
        this.comments = comments;
        this.likeMemberId = likeMemberId;
    }
}
