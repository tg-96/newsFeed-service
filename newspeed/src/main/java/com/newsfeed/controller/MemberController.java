package com.newsfeed.controller;

import com.newsfeed.dto.MemberDto;
import com.newsfeed.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //유저 정보 업데이트
    @PatchMapping("/member")
    public void updateMember(@RequestBody MemberDto memberDto) {
        memberService.updateProfile(memberDto);
    }
    //비밀번호 업데이트
    @PatchMapping("/member/pwd")
    public void updatePwd(@RequestParam String password){
        memberService.updatePassword(password);
    }

}
