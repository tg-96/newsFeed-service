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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;
    private Long likeMemberId;

    public PostLikes(Posts posts, Long likeMemberId) {
        this.posts = posts;
        this.likeMemberId = likeMemberId;
    }
}
