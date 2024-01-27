package com.newsfeed.entity;

import com.newsfeed.common.BaseTimeEntity;
import com.newsfeed.common.Role;
import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@ToString
@NoArgsConstructor
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String email;
    private String password;
    private String role;
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

   @Builder
    public Member(String name,String email, String password,String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public void changeRole(String role){
       this.role = role;
    }

    public void changeName(String name){
       this.name = name;
    }
    public void changeImg(String img){
       this.name = img;
    }
    public void changeIntroduction(String introduction){
       this.introduction = introduction;
    }
    public void changePassword(String password){
       this.password = password;
    }
}
