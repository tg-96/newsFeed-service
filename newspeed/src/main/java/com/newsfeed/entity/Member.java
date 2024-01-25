package com.newsfeed.entity;

import com.newsfeed.common.BaseTimeEntity;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.util.Set;

@Entity
@Getter
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String password;
    private String image;
    private String introduction;
    @OneToMany(mappedBy = "writer")
    private Set<Post> posts; // 내가 작성한 게시글
    @OneToMany(mappedBy = "member")
    private Set<PostLike> postLikes; // 내가 좋아요한 게시글
    @OneToMany(mappedBy = "member")
    private Set<Comment> comments; // 내가 작성한 댓글
    @OneToMany(mappedBy = "member")
    private Set<CommentLike> commentLikes; // 내가 좋아한 댓글

    public Member(String email, String password, String image, String introduction) {
        this.email = email;
        this.password = password;
        this.image = image;
        this.introduction = introduction;
    }

}
