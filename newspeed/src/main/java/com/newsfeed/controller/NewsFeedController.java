package com.newsfeed.controller;

import com.newsfeed.entity.Feeds;
import com.newsfeed.entity.Member;
import com.newsfeed.service.MemberService;
import com.newsfeed.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;
    private final MemberService memberService;
    /**
     * path parmeter의 email을 가지는 대상을 팔로우
     */
    @GetMapping("/newsFeed/follow/{email}")
    public ResponseEntity<Void> follow(@PathVariable("email") String toEmail){
        //현재 사용자 이메일
        String fromEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        newsFeedService.changeFollow(fromEmail,toEmail);
        return ResponseEntity.ok().build();
    }

    /**
     * 현재 사용자의 newsFeed 항목을 조회한다.
     */
    @GetMapping("/newsFeed")
    public List<Feeds> follow(){
        String Email = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberService.findMemberByEmail(Email);
        member.getFeeds()
    }

}
