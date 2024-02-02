package com.preOrderService.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * member service API 요청
 * port :8080
 */
@Service
public class ToMemberService {
    /**
     * 현재 로그인중인 멤버 id 조회
     */
    public Map<String,Object> getCurrentMember(String token) {
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

    /**
     * Email로 회원 정보 조회
     */
    public Map<String,Object> getMemberIdByEmail(String token, String email) {
        WebClient memberClient = WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build();

        //현재 로그인한 사용자
        return memberClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/member")
                                .queryParam("email", email)
                                .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

}
