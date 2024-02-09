package com.preOrderService.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follows {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    private Long fromUserId;
    private Long toUserId;

    public Follows(Long fromUser, Long toUser) {
        this.fromUserId = fromUser;
        this.toUserId = toUser;
    }
}
