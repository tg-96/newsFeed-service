package com.preOrderService.resilienceTest;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name="error-simulation-service",url = "http://localhost:8082")
public interface ResilienceTestClient {
    @GetMapping("/errorful/case1")
    ResponseEntity<String> callCase1();
    @GetMapping("/errorful/case2")
    ResponseEntity<String> callCase2();
    @GetMapping("/errorful/case3")
    ResponseEntity<String> callCase3();
}
