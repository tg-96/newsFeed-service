package com.preOrderService.api.internal;

import com.preOrderService.config.JWTUtil;
import com.preOrderService.dto.RequestActivitiesDto;
import com.preOrderService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalActivitiesController {
    private final ActivityService activityService;
    private final JWTUtil jwtUtil;

    @PostMapping("/activities")
    public ResponseEntity<Void> addActivities(@RequestHeader("Authorization") String token, @RequestBody RequestActivitiesDto request) {
        //토큰 유효성 검사
        if (jwtUtil.isExpired(token)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        activityService.addActivities(request);

        return ResponseEntity.ok().build();
    }
}
