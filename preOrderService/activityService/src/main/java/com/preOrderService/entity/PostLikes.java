package com.preOrderService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class PostLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postLike_id")
    private Long id;
    private Long postId;
    private Long actorId;

    public PostLikes(Long postId, Long actorId) {
        this.postId = postId;
        this.actorId = actorId;
    }
}
