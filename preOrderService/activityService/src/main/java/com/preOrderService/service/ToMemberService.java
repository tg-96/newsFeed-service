package com.preOrderService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * member service API 요청
 */
@Service
public class ToMemberService {
    //base url 설정
    WebClient memberClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    /**
     * memberId로 이름 조회
     */
    public Map<String,Object> getMemberNameById(String token,Long memberId) {

        return memberClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/member").queryParam("id",memberId)
                                .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }
}
