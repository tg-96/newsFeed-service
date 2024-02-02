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
     * 현재 로그인중인 멤버 id 조회
     */
    public Map<String, Object> getCurrentMember(String token) {

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
    public Map<String, Object> getMemberByEmail(String token, String email) {
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
    /**
     * 멤버 정보
     */


}
