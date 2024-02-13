package com.preOrderService.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Posts extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    private String content;
    private String image;
    private Long writerId;
    @Builder
    public Posts(String content, String image, Long writerId) {
        this.content = content;
        this.image = image;
        this.writerId = writerId;
    }
}
