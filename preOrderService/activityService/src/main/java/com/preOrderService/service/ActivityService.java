package com.preOrderService.service;

import com.preOrderService.config.JWTUtil;
import com.preOrderService.dto.RequestActivitiesDto;
import com.preOrderService.dto.ResponseActivitiesDto;
import com.preOrderService.entity.Activities;
import com.preOrderService.entity.ActivityType;
import com.preOrderService.repository.ActivitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivitiesRepository activitiesRepository;
    /**
     * 나의 활동 조회
     */
    public List<ResponseActivitiesDto> findActivities(Long memberId) {
        List<Activities> activities = activitiesRepository.findByOwnerId(memberId); // integer -> Long 타입으로 변환
        return activities.stream().map(activity -> new ResponseActivitiesDto(activity.getNotification()))
                .collect(Collectors.toList());
    }

    /**
     * 활동 추가
     */
    public void addActivities(RequestActivitiesDto request){
        // type: string -> enum 변환
        ActivityType activityType;

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

        Activities activity = new Activities(
                request.getMemberId(),
                activityType,
                request.getFromUserName(),
                request.getToUserName()
        );

        activitiesRepository.save(activity);
    }

}
