package com.preOrderService.api.external;

import com.preOrderService.config.JWTUtil;
import com.preOrderService.dto.*;

import com.preOrderService.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class ExternalNewsFeedController {
    private final NewsFeedService newsFeedService;
    private final JWTUtil jwtUtil;

    /**
     * 피드 조회
     */
    @GetMapping
    public List<FeedsDto> getNewsFeeds(@RequestHeader("Authorization") String token) {
        String parse_token = jwtUtil.parser(token);
        //토큰 유효성 검증
        if (jwtUtil.isExpired(parse_token)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
        Long memberId = jwtUtil.getUserId(parse_token);

        //뉴스피드 조회
        List<FeedsDto> newsFeeds = newsFeedService.getFeeds(token,memberId);

        return newsFeeds;
    }
}
