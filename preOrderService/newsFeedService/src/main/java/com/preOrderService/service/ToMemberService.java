package com.preOrderService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * member service API 요청
 */
@Service
public class ToMemberService {
    /**
     * @param auth
     * jwt token
     * @return
     * memberId
     */
    public Long getCurrentMemberId(String token){
        WebClient memberClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        //현재 로그인한 사용자
        Map<String, Long> response =
                memberClient
                        .get()
                        .uri(uriBuilder ->
                                uriBuilder
                                        .path("/member")
                                        .build())
                        .header("Authorization",auth)
                        .retrieve()
                        .bodyToMono(Map.class)
                        .block();

        // json은 숫자일 경우 Long,Intger 구분 없음. 숫자 크기에 따라 Integer, Long으로 반환, 변환해주어야함.
        Number id = (Number)response.get("id");
    }
}
