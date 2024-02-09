package com.preOrderService.service;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.tcp.TcpClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * member service API 요청
 */
@Service
public class ToUserService {
    //base url 설정
    WebClient userClient = WebClient.builder()
            .clientConnector(new ReactorClientHttpConnector())
            .baseUrl("http://localhost:8080")
            .build();

    /**
     * memberId로 이름 조회
     */
    public String getMemberNameById(String token, Long memberId) {
        String name = userClient
                .get()
                .uri(uriBuilder -> uriBuilder.path("/member").queryParam("id", memberId).build())
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return name;
    }

}
