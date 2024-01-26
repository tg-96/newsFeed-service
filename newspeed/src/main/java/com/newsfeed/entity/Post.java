package com.newsfeed.entity;

import com.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "post_id")
    private Long id;
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;
    @OneToMany(mappedBy = "post")
    private Set<Comment> comments;
    public Post(String content, Member writer) {
        this.content = content;
        this.writer = writer;
    }
}
