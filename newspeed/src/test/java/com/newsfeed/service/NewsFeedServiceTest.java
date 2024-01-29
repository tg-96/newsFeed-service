package com.newsfeed.service;

import com.newsfeed.dto.FeedsDto;
import com.newsfeed.dto.JoinDto;
import com.newsfeed.dto.PostsDto;
import com.newsfeed.entity.Activities;
import com.newsfeed.entity.Member;
import com.newsfeed.repository.ActivitiesRepository;
import com.newsfeed.repository.FollowsRepository;
import com.newsfeed.repository.PostsRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    static MemberService joinService;
    @Autowired
    MemberService memberService;
    @Autowired
    PostsRepository postsRepository;

    @BeforeAll
    public static void init(@Autowired ApplicationContext context) {
        joinService = context.getBean(MemberService.class);
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

    @Test
    public void 포스트_작성(){
        //given
        //팔로우 aaa->bbb
        newsFeedService.changeFollow("aaa@aaa", "bbb@aaa");

        //bbb가 post 작성
        PostsDto postsDto = new PostsDto("포스트를 작성했습니다.",null);
        newsFeedService.writePost("bbb@aaa",postsDto);
        System.out.println("여기가 문제인가???");

        //then
        // 본인 피드 확인
        List<FeedsDto> feeds1 = newsFeedService.getFeeds("bbb@aaa");
        assertThat(feeds1.get(0).getText()).isEqualTo("포스트를 작성했습니다.");

        // 팔로워 피드 확인
        List<FeedsDto> feeds2 = newsFeedService.getFeeds("aaa@aaa");
        assertThat(feeds2.get(0).getText()).isEqualTo("포스트를 작성했습니다.");

        // 팔로우한 사람의 활동 확인
        Member member = memberService.findMemberByEmail("aaa@aaa");
        Optional<Activities> activities = activitiesRepository.findByOwner(member.getId());
        assertThat(activities.get().getNotification()).isEqualTo("bbb@aaa님이 게시물을 작성 했습니다.");
    }

    @Test
    public void 댓글_작성(){}
}