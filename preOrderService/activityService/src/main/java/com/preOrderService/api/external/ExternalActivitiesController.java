package com.preOrderService.api.external;

import com.preOrderService.config.JWTUtil;
import com.preOrderService.dto.ResponseActivitiesDto;
import com.preOrderService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExternalActivitiesController {
    private final ActivityService activityService;
    private final JWTUtil jwtUtil;
    /**
     * 팔로우한 유저들의 활동 조회
     * notification + 해당 activity(post,comment,postlike,commentlike)정보
     */
    @GetMapping("/api/activities")
    public List<ResponseActivitiesDto> getActivities(@RequestHeader("Authorization")String token) {
        //token 유효기간 검증
        if(jwtUtil.isExpired(token)){
            throw new RuntimeException("유효하지 않은 토큰입니다.");
        }
        Long memberId = jwtUtil.getUserId(token);

        // 현재 접속한 회원의 활동 조회
        return activityService.findActivities(memberId);
    }
}
