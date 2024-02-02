package com.preOrderService.entity;

import com.preOrderService.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comments extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;
    private String text;
    private Long writeMemberId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts posts;
    public Comments(Long writeMemberId, Posts post,String text) {
        this.text = text;
        this.writeMemberId = writeMemberId;
        this.posts = post;
    }
}

