package com.newsfeed.jwt;

import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

@Transactional
class JwtTokenProviderTest {
    @Test
    public void 토큰() {
        JwtTokenProvider jwtTokenProvider = new JwtTokenProvider("38c992e7e54afbbb495e07efff6a6ca250d38f0039f27434d6fd2cf30972b54a");
        System.out.println("jwtTokenProvider = " + jwtTokenProvider.getKey());
    }
}