package com.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Follows {
    @Id
    @GeneratedValue
    @Column(name = "follow_id")
    private Long id;
    private Long fromUser;
    private Long toUser;

    public Follows(Long fromUser, Long toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
