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

    /**
     * 팔로워에게 활동 추가
     * 희찬 ->(follow) 흥민
     * ex) 희찬: 흥민님이 강인님의 게시물에 댓글을 달았습니다.
     */
    @PostMapping("/activities")
    public ResponseEntity<Void> addActivities(@RequestBody RequestActivitiesDto request) {
        activityService.addFollowerActivities(request);
        return ResponseEntity.ok().build();
    }

    /**
     * 게시물의 주인에게 활동 추가
     * ex) 흥민님이 내 게시물에 댓글을 달았습니다.
     * ex) 흥민님이 내 게시물을 좋아합니다.
     */
    @PostMapping("/activities/owner")
    public ResponseEntity<Void> addActivitiesToOwner( @RequestBody RequestActivitiesDto request) {
        activityService.addMyActivities(request);
        return ResponseEntity.ok().build();
    }
}
