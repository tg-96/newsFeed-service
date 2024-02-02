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
    private String fromEmail;
    private String toEmail;

    @Builder
    public Activities(Long memberId,ActivityType type, String fromEmail, String toEmail) {
        this.memberId = memberId;
        this.type = type;
        this.fromEmail = fromEmail;
        this.toEmail = toEmail;

        switch (type){
            case FOLLOWS -> notification = fromEmail+"님이 "+toEmail+"을 팔로우 했습니다.";
            case COMMENTS -> notification = fromEmail+"님이 "+toEmail+"님의 글에 댓글을 작성했습니다.";
            case COMMENT_LIKE -> notification = fromEmail+"님이 "+toEmail+"님의 댓글을 좋아합니다.";
            case POSTS -> notification = fromEmail+"님이 게시물을 작성 했습니다.";
            case POST_LIKES -> notification = fromEmail+"님이 "+toEmail+"님의 글을 좋아합니다.";
        }
    }
    // 내 포스트에 댓글을 작성했을 경우 notificaion 변경
    // ex) B님이 내 포스트에 댓글을 남겼습니다.
    public void changeNotification(String notification){
        this.notification = notification;
    }

}
