package com.preOrderService.service;

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
    private final ToMemberService toMemberService;

    /**
     * 나의 활동 조회
     */
    public List<ResponseActivitiesDto> findActivities(String token) {
        Map<String, Object> currentMember = toMemberService.getCurrentMember(token);
        // json은 숫자일 경우 Long,Intger 구분 없음. 숫자 크기에 따라 Integer,Long으로 반환, 변환해주어야함.
        Number id = (Number)currentMember.get("id");
        List<Activities> activities = activitiesRepository.findByOwnerId(id.longValue()); // integer -> Long 타입으로 변환
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
            case "POSTS":
                activityType = ActivityType.POSTS;
            case "POST_LIKES":
                activityType = ActivityType.POST_LIKES;
            default:
                throw new RuntimeException("활동 타입이 올바르지 않습니다.");
        }

        Activities activity = new Activities(
                request.getMemberId(),
                activityType,
                request.getFromEmail(),
                request.getToEmail()
        );

        activitiesRepository.save(activity);
    }

}
