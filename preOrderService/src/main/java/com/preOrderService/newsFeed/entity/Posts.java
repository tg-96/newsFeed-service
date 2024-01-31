package com.preOrderService.newsFeed.entity;

import com.preOrderService.common.BaseTimeEntity;
import com.preOrderService.member.entity.Member;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
    @Builder
    public Posts(String content, String image, Member writer) {
        this.content = content;
        this.image = image;
        this.writer = writer;
    }
}
