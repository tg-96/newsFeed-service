package com.preOrderService.resilienceTest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.util.Random;

@RestController
public class ResilienceTestController {
    /**
     *  요청의 약 5%에서 500 (Internal Server Error)를 반환,
     *  나머지 95%의 요청에서는 정상적인 응답을 반환.
     *  이는 서비스가 간헐적으로 실패하는 상황을 시뮬레이션.
     */
    @GetMapping("/errorful/case1")
    public ResponseEntity<String> case1() {
        // Simulate 5% chance of 500 error
        if (new Random().nextInt(100) < 5) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }

        return ResponseEntity.ok("Normal response");
    }

    /**
     *  매 분의 처음 10초 동안 이 엔드포인트에 대한 모든 요청은 10초간 지연,
     *  이후 503 (Service Unavailable) 응답을 반환.
     *  이는 서비스가 과부하 상태일 때 요청을 처리하지 못하고 지연되는 상황을 시뮬레이션.
     */
    @GetMapping("/errorful/case2")
    public ResponseEntity<String> case2() {
        // Simulate blocking requests every first 10 seconds
        LocalTime currentTime = LocalTime.now();
        int currentSecond = currentTime.getSecond();

        if (currentSecond < 10) {
            // Simulate a delay (block) for 10 seconds
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return ResponseEntity.status(503).body("Service Unavailable");
        }

        return ResponseEntity.ok("Normal response");
    }

    /**
     *  시나리오 설명:
     *  매 분의 처음 10초 동안 이 엔드포인트는 500 (Internal Server Error) 응답을 반환.
     *  특정 시간대에 서비스가 실패하는 상황을 시뮬레이션.
     */
    @GetMapping("/errorful/case3")
    public ResponseEntity<String> case3() {
        // Simulate 500 error every first 10 seconds
        LocalTime currentTime = LocalTime.now();
        int currentSecond = currentTime.getSecond();

        if (currentSecond < 10) {
            return ResponseEntity.status(500).body("Internal Server Error");
        }

        return ResponseEntity.ok("Normal response");
    }
}