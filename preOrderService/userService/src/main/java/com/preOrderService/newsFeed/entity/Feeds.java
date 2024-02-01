package com.preOrderService.newsFeed.entity;

import com.preOrderService.common.BaseTimeEntity;
import com.preOrderService.member.entity.Member;
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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member owner;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    public Feeds(Posts post,Member owner) {
        this.post = post;
        this.owner = owner;
    }
}
