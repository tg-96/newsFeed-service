package com.newsfeed.controller;

import com.newsfeed.dto.JoinDto;
import com.newsfeed.dto.MemberDto;
import com.newsfeed.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinDto joinDto){
        memberService.join(joinDto);

        return ResponseEntity.ok().build();
    }

}
