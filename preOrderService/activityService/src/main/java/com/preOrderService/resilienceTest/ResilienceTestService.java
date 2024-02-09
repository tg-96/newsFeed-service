package com.preOrderService.resilienceTest;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@RestController
public class ResilienceTestService {

    @Autowired
    private ResilienceTestClient errorSimulationClient;

    /**
     * 5%의 확률로 에러 발생 상황 -> 실패했을때 retry
     */
    @CircuitBreaker(name = "test")
    @Retry(name = "test",fallbackMethod = "fallback")
//    @Scheduled(fixedRate = 100)
    public void simulationErrors1(){
        // Case 1 호출
        LocalDateTime start = LocalDateTime.now();

        ResponseEntity<String> response1 = errorSimulationClient.callCase1();
        System.out.println("Case 1 Response: " + response1.getBody());

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start,end);
        System.out.println("------------------------------------");
        System.out.println("시작 시간: "+start);
        System.out.println("끝난 시간: "+end);
        System.out.println("걸린시간: "+ duration.getSeconds()+"초");
        System.out.println("--------------------------------------");

    }

    /**
     * 트래픽이 몰려 호출이 길어지는 상황 (10초 지연 후 호출)
     */
    @CircuitBreaker(name = "test",fallbackMethod = "fallback")
    @Retry(name="test")
//    @Scheduled(fixedRate = 500)
    public void simulationErrors2(){
        // Case 2 호출
        LocalDateTime start = LocalDateTime.now();

        ResponseEntity<String> response2 = errorSimulationClient.callCase2();
        System.out.println("Case 2 Response: " + response2.getBody());

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start,end);
        System.out.println("------------------------------------");
        System.out.println("시작 시간: "+start);
        System.out.println("끝난 시간: "+end);
        System.out.println("걸린시간: "+ duration.getSeconds()+"초");
        System.out.println("--------------------------------------");

    }

    /**
     * 매분의 10초까지 500에러 반환.
     * 특정 시간대에 에러 발생
     */
    @CircuitBreaker(name = "test",fallbackMethod = "fallback")
    @Retry(name = "test")
    @Scheduled(fixedRate = 500)
    public void simulationErrors3(){
        // Case 3 호출
        LocalDateTime start = LocalDateTime.now();

        ResponseEntity<String> response3 = errorSimulationClient.callCase3();
        System.out.println("Case 3 Response: " + response3.getBody());

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start,end);

        System.out.println("------------------------------------");
        System.out.println("시작 시간: "+start);
        System.out.println("끝난 시간: "+end);
        System.out.println("걸린시간: "+ duration.getSeconds()+"초");
        System.out.println("--------------------------------------");

    }
    public void fallback(Throwable ex){
        System.out.println("Fallback method called. Error: " + ex.getMessage());
        System.out.println("현재시간: " + LocalDateTime.now());
        System.out.println("----------------------------------------------------");

    }
}
