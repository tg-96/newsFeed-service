package com.newsfeed.service;

import com.newsfeed.dto.JoinDto;
import com.newsfeed.repository.FollowRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NewsFeedServiceTest {
    @Autowired
    NewsFeedService followService;
    @Autowired
    FollowRepository followRepository;
    @Autowired
    MemberService joinService;
    @Autowired
    MemberService memberService;
    @Test
    public void 팔로우(){
        //given
        JoinDto joinDto1 = new JoinDto();
        joinDto1.setName("율");
        joinDto1.setRole("USER");
        joinDto1.setPassword("123");
        joinDto1.setEmail("abc@aaa");

        JoinDto joinDto2 = new JoinDto();
        joinDto2.setName("규");
        joinDto2.setRole("USER");
        joinDto2.setPassword("123");
        joinDto2.setEmail("cba@aaa");

        joinService.join(joinDto1);
        joinService.join(joinDto2);
        Long fromMemberId = memberService.findMemberByEmail("abc@aaa").getId();
        Long toMemberId = memberService.findMemberByEmail("cba@aaa").getId();

        //when
        String message = followService.changeFollow("abc@aaa","cba@aaa");

        //then
        List<Long> followLists = followRepository.findFollowLists(fromMemberId);
        assertThat(followLists.get(0)).isEqualTo(toMemberId);
        assertThat(message).isEqualTo("abc@aaa 님이 cba@aaa 님을 팔로우 했습니다.");
    }
}