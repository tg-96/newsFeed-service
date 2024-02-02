package com.preOrderService.service;

import com.preOrderService.dto.ActivitiesDto;
import com.preOrderService.entity.Activities;
import com.preOrderService.repository.ActivitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivitiesRepository activitiesRepository;
    // to memberService
    WebClient memberClient = WebClient.builder()
            .baseUrl("http://localhost:8080")
            .build();

    /**
     * 나의 활동 조회
     */
    public List<ActivitiesDto> findActivities(String auth) {
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

        // json은 숫자일 경우 Long,Intger 구분 없음. 숫자 크기에 따라 Integer,Long으로 반환, 변환해주어야함.
        Number id = (Number)response.get("id");
        List<Activities> activities = activitiesRepository.findByOwnerId(id.longValue()); // integer -> Long 타입으로 변환
        return activities.stream().map(activity -> new ActivitiesDto(activity.getNotification()))
                .collect(Collectors.toList());
    }

}
