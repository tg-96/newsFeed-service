package com.preOrderService.service;

import com.preOrderService.dto.RequestActivitiesDto;
import com.preOrderService.dto.ResponseActivitiesDto;
import com.preOrderService.entity.Activities;
import com.preOrderService.entity.ActivityType;
import com.preOrderService.repository.ActivitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivitiesRepository activitiesRepository;
    ActivityType activityType;

    /**
     * 나의 활동 조회
     */
    public List<ResponseActivitiesDto> findActivities(Long memberId) {
        List<Activities> activities = activitiesRepository.findByOwnerId(memberId); // integer -> Long 타입으로 변환
        return activities.stream().map(activity -> new ResponseActivitiesDto(activity.getNotification()))
                .collect(Collectors.toList());
    }

    /**
     * 팔로워들의 활동에 추가
     */
    public void addFollowerActivities(RequestActivitiesDto request){
        //활동 타입 결정
        defineActivityType(request);

        Activities activity = new Activities(
                request.getMemberId(),
                activityType,
                request.getFromUserName(),
                request.getToUserName()
        );

        activitiesRepository.save(activity);
    }



    /**
     * 나의 활동에 추가
     * ex) 흥민님이 내 게시물에 좋아요를 눌렀습니다.
     */

    public void addMyActivities(RequestActivitiesDto request){

        //활동 타입 결정
        defineActivityType(request);

        Activities activity = new Activities(
                request.getMemberId(),
                activityType,
                request.getFromUserName(),
                null
        );


        String notification ="";
        //활동에 따른 알림
        switch (activity.getType()){
            case FOLLOWS -> notification =  request.getFromUserName()+"님이 나를 팔로우 했습니다.";
            case COMMENTS -> notification =  request.getFromUserName()+"님이 내 게시물에 댓글을 작성했습니다.";
            case COMMENT_LIKE -> notification =  request.getFromUserName()+"님이 내 댓글을 좋아합니다.";
            case POSTS -> throw new RuntimeException("포스트 작성은 내 활동에 추가할 수 없습니다.");
            case POST_LIKES -> notification =  request.getFromUserName()+"님이 내 게시물을 좋아합니다.";
        }

        activity.changeNotification(notification);

        activitiesRepository.save(activity);
    }

    private void defineActivityType(RequestActivitiesDto request) {
        switch (request.getType()){
            case "FOLLOWS":
                activityType = ActivityType.FOLLOWS;
                break;
            case "COMMENT_LIKE":
                activityType = ActivityType.COMMENT_LIKE;
                break;
            case "COMMENTS":
                activityType = ActivityType.COMMENTS;
                break;
            case "POSTS":
                activityType = ActivityType.POSTS;
                break;
            case "POST_LIKES":
                activityType = ActivityType.POST_LIKES;
                break;
            default:
                throw new RuntimeException("활동 타입이 올바르지 않습니다.");
        }
    }


}
