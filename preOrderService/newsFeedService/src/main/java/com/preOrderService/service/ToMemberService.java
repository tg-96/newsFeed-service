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
    public String getMemberNameById(String token, Long memberId) {

        Map map = memberClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/member").queryParam("id", memberId)
                                .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
        String name = (String) map.get("name");
        return name;
    }

    /**
     * 멤버id로 정보 조회
     */
    public Map<String, Object> getMemberById(String token, Long memberId) {
        //현재 로그인한 사용자
        return memberClient
                .get()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/member")
                                .queryParam("memberId", memberId)
                                .build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(Map.class)
                .block();
    }

}
