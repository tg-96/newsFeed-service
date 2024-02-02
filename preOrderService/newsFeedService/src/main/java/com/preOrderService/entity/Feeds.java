package com.preOrderService.entity;

import com.preOrderService.common.BaseTimeEntity;
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
    private Long memberId;
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Posts post;

    public Feeds(Posts post,Long memberId) {
        this.post = post;
        this.memberId = memberId;
    }
}
