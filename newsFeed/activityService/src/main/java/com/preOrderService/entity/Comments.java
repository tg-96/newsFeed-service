package com.preOrderService.entity;

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
    private Long writerId;
    private Long postId;
    public Comments(Long writerId, Long postId,String text) {
        this.text = text;
        this.writerId = writerId;
        this.postId = postId;
    }
}

