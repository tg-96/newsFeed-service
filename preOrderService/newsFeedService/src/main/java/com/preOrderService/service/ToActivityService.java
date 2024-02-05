package com.preOrderService.service;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ToActivityService {
    private final ToMemberService toMemberService;
    // base url 설정
    WebClient activityClient = WebClient.builder()
            .baseUrl("http://localhost:8081")
            .build();

    /**
     * 팔로워들의 activity에 활동 추가 요청
     * TODO: 팔로워 수가 많을 경우 API 요청이 너무 많음.
     */
    public void addActivitiesToFollowers(String token, List<Long> followerId, Long fromUserId, Long toUserId, String type) {

        System.out.println("token = " + token + ", followerId = " + followerId + ", fromUserId = " + fromUserId + ", toUserId = " + toUserId + ", type = " + type);
        //body에 들어갈 내용 추가
        Map<String, Object> bodyMap = new HashMap<>();
        //회원 이름
        String fromUserName = "";
        String toUserName = "";

        if (fromUserId != null) {
            fromUserName = toMemberService.getMemberNameById(token, fromUserId);
        }
        System.out.println("fromUserName = " + fromUserName);
        if (toUserId != null) {
            toUserName = toMemberService.getMemberNameById(token, toUserId);
        }
        System.out.println("toUserName = " + toUserName);
        bodyMap.put("fromUserName", fromUserName);
        bodyMap.put("toUserName", toUserName);
        bodyMap.put("type", type);

        followerId.stream().filter(id -> !id.equals(toUserId)).forEach(
                id -> {
                    System.out.println("id = " + id);
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
                            .subscribe(result -> {
                                // 결과 처리 로직
                                System.out.println("API 호출 결과: " + result);
                            });
                }
        );
    }

    /**
     * 누군가 댓글이나 좋아요를 남겼을때, 내 활동에 알림을 남겨줌.
     */
    public void addActivityToOwner(String token, Long memberId, Long fromUserId, String type) {
        System.out.println("token = " + token + ", memberId = " + memberId + ", fromUserId = " + fromUserId + ", type = " + type);
        //이름
        String fromUserName = toMemberService.getMemberNameById(token, fromUserId);
        //body에 들어갈 내용 추가
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("memberId", memberId);
        bodyMap.put("fromUserName", fromUserName);
        bodyMap.put("toUserName","");
        bodyMap.put("type", type);

        activityClient
                .post()
                .uri(uriBuilder ->
                        uriBuilder
                                .path("/activities/owner")
                                .build())
                .header("Authorization", token)
                .bodyValue(bodyMap)
                .retrieve()
                .bodyToMono(Map.class)
                .subscribe(result -> {
                    // 결과 처리 로직
                    System.out.println("API 호출 결과: " + result);
                });
    }
}
