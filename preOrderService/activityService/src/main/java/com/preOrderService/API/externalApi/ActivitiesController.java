package com.preOrderService.API.externalApi;

import com.preOrderService.dto.ResponseActivitiesDto;
import com.preOrderService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ActivitiesController {
    private final ActivityService activityService;

    /**
     * 팔로우한 유저들의 활동 조회
     * notification + 해당 activity(post,comment,postlike,commentlike)정보
     */
    @GetMapping("/api/activities")
    public List<ResponseActivitiesDto> getActivities(@RequestHeader("Authorization")String auth) {
        // 현재 접속한 회원의 활동 조회
        return activityService.findActivities(auth);
    }

}
