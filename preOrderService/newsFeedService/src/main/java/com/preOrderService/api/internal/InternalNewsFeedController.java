package com.preOrderService.api.internal;

import com.preOrderService.dto.request.RequestFeed;
import com.preOrderService.entity.Feeds;
import com.preOrderService.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/feed")
public class InternalNewsFeedController {
    private final NewsFeedService newsFeedService;
    /**
     * 피드 생성
     */
    @PostMapping
    public void createFeed(@RequestBody RequestFeed req) {
        newsFeedService.createFeed(req);
    }
}
