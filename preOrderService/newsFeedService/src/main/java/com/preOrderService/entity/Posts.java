package com.preOrderService.entity;

import com.preOrderService.common.BaseTimeEntity;
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
    private Long writeMemberId;
    @Builder
    public Posts(String content, String image, Long writeMemberId) {
        this.content = content;
        this.image = image;
        this.writeMemberId = writeMemberId;
    }
}
