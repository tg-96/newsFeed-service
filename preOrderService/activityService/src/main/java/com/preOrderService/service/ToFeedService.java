package com.preOrderService.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class ToFeedService {
    private static final Logger logger = LoggerFactory.getLogger(ToFeedService.class);
    WebClient feedClient = WebClient.builder()
            .baseUrl("http://localhost:8082")
            .build();

    /**
     * 피드에 추가
     */
    @Transactional
    public void addToFeed(String token, Long memberId, Long postId) {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("userId", memberId);
        bodyMap.put("postId", postId);

        Map<String,Object> map = feedClient
                .post()
                .uri("/feed")
                .header("Authorization", token)
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

}
