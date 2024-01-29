package com.newsfeed.controller;

import com.newsfeed.dto.JoinDto;
import com.newsfeed.dto.MemberDto;
import com.newsfeed.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    //유저 정보 업데이트
    @PatchMapping("/member")
    public void updateMember(@RequestBody MemberDto memberDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        memberService.updateProfile(memberDto,email);
    }
    //비밀번호 업데이트
    @PutMapping("/member/pwd")
    public void updatePwd(@RequestParam String password){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        memberService.updatePassword(password,email);
    }
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinDto joinDto){
        memberService.join(joinDto);

        return ResponseEntity.ok().build();
    }

}
