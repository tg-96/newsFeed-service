package com.newsfeed.controller;

import com.newsfeed.dto.JoinDto;
import com.newsfeed.dto.MemberDto;
import com.newsfeed.dto.PwdUpdateDto;
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

    /**
     * 회원 정보 조회
     */
    @GetMapping("/member")
    public MemberDto findMember(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return memberService.findMember(email);
    }

    /**
     *유저 정보 업데이트
     */
    @PatchMapping("/member")
    public ResponseEntity<Void> updateMember(@RequestBody MemberDto memberDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        memberService.updateProfile(memberDto,email);

        return ResponseEntity.ok().build();
    }

    /**
     * 비밀번호 업데이트
     **/
    @PutMapping("/member/pwd")
    public ResponseEntity<Void> updatePwd(@RequestBody PwdUpdateDto pwdUpdateDto){
        System.out.println("password = " + pwdUpdateDto.getPassword());
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email = " + email);
        memberService.updatePassword(pwdUpdateDto.getPassword(), email);
        return ResponseEntity.ok().build();
    }

    /**
     * 회원가입
     */
    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody JoinDto joinDto){
        memberService.join(joinDto);

        return ResponseEntity.ok().build();
    }

}
