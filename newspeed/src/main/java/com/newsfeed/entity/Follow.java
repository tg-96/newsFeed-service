package com.newsfeed.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;
    private Long fromUser;
    private Long toUser;

    public Follow(Long fromUser, Long toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
