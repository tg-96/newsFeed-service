package com.preOrderService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

/**
 * acitivity service에 api 요청
 * port: 8081
 */
@Service
public class ToActivityService {
    public Map<String,Object> addActivities(String token, List<Long> memberId,String fromEmail,String toEmail) {
        WebClient memberClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        return memberClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/member")
                                .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Map.class)

                .block();
}
