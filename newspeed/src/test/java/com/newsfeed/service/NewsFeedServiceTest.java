package com.newsfeed.service;

import com.newsfeed.dto.JoinDto;
import com.newsfeed.entity.Member;
import com.newsfeed.repository.ActivitiesRepository;
import com.newsfeed.repository.FollowsRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class NewsFeedServiceTest {
    @Autowired
    NewsFeedService newsFeedService;
    @Autowired
    FollowsRepository followRepository;
    @Autowired
    ActivitiesRepository activitiesRepository;
    @Autowired
    MemberService joinService;
    @Autowired
    MemberService memberService;

    @PostConstruct
    public void init() {
        JoinDto joinDto1 = new JoinDto();
        joinDto1.setName("율이");
        joinDto1.setRole("USER");
        joinDto1.setPassword("123");
        joinDto1.setEmail("aaa@aaa");

        JoinDto joinDto2 = new JoinDto();
        joinDto2.setName("태규");
        joinDto2.setRole("USER");
        joinDto2.setPassword("123");
        joinDto2.setEmail("bbb@aaa");

        JoinDto joinDto3 = new JoinDto();
        joinDto3.setName("준석");
        joinDto3.setRole("USER");
        joinDto3.setPassword("123");
        joinDto3.setEmail("ccc@aaa");

        joinService.join(joinDto1);
        joinService.join(joinDto2);
        joinService.join(joinDto3);
    }

    @Test
    public void 팔로우() {
        //given
        Member fromMember = memberService.findMemberByEmail("aaa@aaa");
        Member toMember = memberService.findMemberByEmail("bbb@aaa");

        //when
        String message = newsFeedService.changeFollow("aaa@aaa", "bbb@aaa");
        newsFeedService.changeFollow("bbb@aaa", "ccc@aaa");

        //then
        ///follow 메세지 리턴
        List<Long> followLists = followRepository.findFollowLists(fromMember.getId());
        assertThat(followLists.get(0)).isEqualTo(toMember.getId());
        assertThat(message).isEqualTo("aaa@aaa 님이 bbb@aaa 님을 팔로우 했습니다.");

        ///bbb의 활동이 aaa에게 뜨는지 확인
        Member aaa = memberService.findMemberByEmail("aaa@aaa");

        assertThat(
                activitiesRepository.findByOwner(aaa.getId())
                        .get()
                        .getNotification()
        ).isEqualTo("bbb@aaa" + "님이 " + "ccc@aaa" + "을 팔로우 했습니다.");
    }

//    @Test
//    public void 포스트_작성(){
//        //
//    }
}