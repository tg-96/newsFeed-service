package com.preOrderService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * acitivity service에 api 요청
 * port: 8081
 */
@Service
public class ToActivityService {
    // base url 설정
    WebClient activityClient = WebClient.builder()
            .baseUrl("http://localhost:8081")
            .build();

    /**
     * 팔로워들의 activity에 활동 추가 요청
     * TODO: 팔로워 수가 많을 경우 API 요청이 너무 많음.
     */
    public void addActivities(String token, List<Long> followerId, String fromUserName, String toUserName,String type) {

        //body에 들어갈 내용 추가
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("fromUserName", fromUserName);
        bodyMap.put("toUserName", toUserName);
        bodyMap.put("type", type);

        followerId.stream().forEach(
                id -> {
                    bodyMap.put("memberId", id);
                    activityClient
                            .post()
                            .uri(uriBuilder ->
                                    uriBuilder
                                            .path("/activities")
                                            .build())
                            .header("Authorization", token)
                            .bodyValue(bodyMap)
                            .retrieve()
                            .bodyToMono(Map.class)
                            .block();
                }
        );
    }
}
