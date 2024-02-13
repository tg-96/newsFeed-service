package com.preOrderService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Feeds extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feeds_id")
    private Long id;
    private Long ownerId;
    private Long postId;

    public Feeds(Long postId,Long ownerId) {
        this.postId = postId;
        this.ownerId = ownerId;
    }
}
