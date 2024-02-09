package com.preOrderService.service;

import com.preOrderService.entity.Activities;
import com.preOrderService.entity.ActivityType;
import com.preOrderService.repository.ActivitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 활동 내역 알림 생성 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    //활동 타입
    private final ActivitiesRepository activitiesRepository;
    private final ToUserService toMemberService;

    /**
     * 나의 활동을 팔로워 알림에 추가
     */
    public void addActToFollower(String token, Long memberId, Long fromMemberId, Long toMemberId, ActivityType type) {
        System.out.println("token = " + token + ", memberId = " + memberId + ", fromMemberId = " + fromMemberId + ", toMemberId = " + toMemberId + ", type = " + type);
        String fromUserName ="";
        if (fromMemberId != null) {
            fromUserName = toMemberService.getMemberNameById(token, fromMemberId);
            System.out.println("fromUserName = " + fromUserName);
        }

        String toUserName ="";
        if (toMemberId != null) {
            toUserName = toMemberService.getMemberNameById(token, toMemberId);
            System.out.println("toUserName = " + toUserName);
        }

        Activities activity = new Activities(memberId, type, fromUserName, toUserName);
        activitiesRepository.save(activity);
    }

    /**
     * 작성자 알림에 추가
     * ex) 흥민님이 내 게시물에 좋아요를 눌렀습니다.
     */

    public void addActToWriter(String token, Long memberId, Long fromMemberId, ActivityType type) {
        String fromUserName = toMemberService.getMemberNameById(token, fromMemberId);

        Activities activity = new Activities(memberId, type, fromUserName, null);
        String notification = getNotificationMessage(fromUserName, type); // 알림 메시지 생성 로직을 별도의 메소드로 분리
        activity.changeNotification(notification);

        activitiesRepository.save(activity);
    }

    private String getNotificationMessage(String fromUserName, ActivityType type) {
        switch (type) {
            case FOLLOWS:
                return fromUserName + "님이 나를 팔로우 했습니다.";
            case COMMENTS:
                return fromUserName + "님이 내 게시물에 댓글을 작성했습니다.";
            case COMMENT_LIKE:
                return fromUserName + "님이 내 댓글을 좋아합니다.";
            case POSTS:
                throw new RuntimeException("포스트 작성은 내 활동에 추가할 수 없습니다.");
            case POST_LIKES:
                return fromUserName + "님이 내 게시물을 좋아합니다.";
            default:
                throw new IllegalArgumentException("알 수 없는 활동 타입입니다.");
        }
    }

}
