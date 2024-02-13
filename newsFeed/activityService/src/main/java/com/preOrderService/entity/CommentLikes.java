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
    private Long commentId;
    private Long actorId;

    public CommentLikes(Long commentId,Long actorId) {
        this.commentId = commentId;
        this.actorId = actorId;
    }
}
