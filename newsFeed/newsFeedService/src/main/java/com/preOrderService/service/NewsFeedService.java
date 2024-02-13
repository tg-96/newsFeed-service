package com.preOrderService.service;

import com.preOrderService.dto.FeedsDto;
import com.preOrderService.dto.request.RequestFeed;
import com.preOrderService.entity.Feeds;
import com.preOrderService.repository.FeedsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsFeedService {
    private final FeedsRepository feedsRepository;

    /**
     * 피드 조회
     */
    public List<FeedsDto> getFeeds(String token, Long memberId) {
        System.out.println("token = " + token + ", memberId = " + memberId);
        // 피드 조회
        List<Feeds> feeds = feedsRepository.findByOwner(memberId);
        for(Feeds feed: feeds){
            System.out.println("feed.getPostId() = " + feed.getPostId());
        }

        //feeds가 없으면 예외 처리
        if (feeds.isEmpty()) {
            throw new RuntimeException("피드가 비어있습니다.");
        }

        //postId 리스트 최신 순으로 조회
        List<Long> postIdList = feedsRepository.findPostIdList(memberId);

        // base url 설정
        WebClient activityClient = WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build();
        /**
         * TODO: postID 하나당 한번 API 호출 하므로, 성능이 안좋음. 개선해야함
         */
        //피드 조회, 순서 중요하므로 ArrayList 사용
        List<FeedsDto> feedsDtoList = new ArrayList<>();

        postIdList.stream().forEach(id -> {
            Map map = activityClient
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/activities/post")
                            .queryParam("postId", id)
                            .build()
                    )
                    .header("Authorization", token)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            feedsDtoList.add(FeedsDto.builder()
                    .text((String) map.get("text"))
                    .writerId(Long.valueOf((Integer) map.get("writerId")))
                    .image((String) map.get("image"))
                    .createDate(LocalDateTime.parse((String) map.get("createDate"), DateTimeFormatter.ISO_DATE_TIME))
                    .updateDate(LocalDateTime.parse((String) map.get("updateDate"), DateTimeFormatter.ISO_DATE_TIME))
                    .build());
        });

        return feedsDtoList;

    }

/**
 * 피드 생성
 */
    @Transactional
    public void createFeed(RequestFeed req) {
        Feeds feed = new Feeds(req.getPostId(), req.getUserId());
        feedsRepository.save(feed);
    }


}
