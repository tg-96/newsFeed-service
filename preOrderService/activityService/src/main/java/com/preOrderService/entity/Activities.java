package com.preOrderService.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Activities extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long id;
    private Long memberId;
    @Enumerated(EnumType.STRING)
    private ActivityType type;
    private String notification;
    private String actorEmail;
    private String targetEmail;

    @Builder
    public Activities(Long member_id,ActivityType type, String actorEmail, String targetEmail) {
        this.memberId = member_id;
        this.type = type;
        this.actorEmail = actorEmail;
        this.targetEmail = targetEmail;

        switch (type){
            case FOLLOWS -> notification = actorEmail+"님이 "+targetEmail+"을 팔로우 했습니다.";
            case COMMENTS -> notification = actorEmail+"님이 "+targetEmail+"님의 글에 댓글을 작성했습니다.";
            case COMMENT_LIKE -> notification = actorEmail+"님이 "+targetEmail+"님의 댓글을 좋아합니다.";
            case POSTS -> notification = actorEmail+"님이 게시물을 작성 했습니다.";
            case POST_LIKES -> notification = actorEmail+"님이 "+targetEmail+"님의 글을 좋아합니다.";
        }
    }
    // 내 포스트에 댓글을 작성했을 경우 notificaion 변경
    // ex) B님이 내 포스트에 댓글을 남겼습니다.
    public void changeNotification(String notification){
        this.notification = notification;
    }

}
