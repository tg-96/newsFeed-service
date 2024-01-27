package com.newsfeed.entity;

import com.newsfeed.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Deque;
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
    private Set<Posts> posts; // 내가 작성한 게시글
    @OneToMany
    @JoinColumn(name = "postLike_id")
    private Set<PostLikes> postLikes; // 내가 좋아요한 게시글
    @OneToMany(mappedBy = "member")
    private Set<Comments> comments; // 내가 작성한 댓글
    @OneToMany
    @JoinColumn(name = "commentLike_id")
    private Set<CommentLikes> commentLikes; // 내가 좋아한 댓글
    @OneToMany
    @JoinColumn(name = "activity_id")
    private Deque<Activity> activities; // 팔로우한 유저들의 활동
    @OneToMany
    @JoinColumn(name = "feeds_id")
    private Deque<Feeds> feeds; // 팔로우한 유저들의 피드

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
