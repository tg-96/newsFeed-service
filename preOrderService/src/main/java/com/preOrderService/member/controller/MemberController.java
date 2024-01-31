package com.preOrderService.member.controller;

import com.preOrderService.member.dto.JoinDto;
import com.preOrderService.member.service.AwsS3Service;
import com.preOrderService.newsFeed.dto.MemberDto;
import com.preOrderService.member.dto.PwdUpdateDto;
import com.preOrderService.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final AwsS3Service awsS3Service;

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
    public ResponseEntity<Void> updateMember(@RequestPart(value = "memberDto") MemberDto memberDto,
                                             @RequestPart(value = "file")MultipartFile multipartFile) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //이미지 정보 s3에 업로드후, 프로필 이미지 추가
        if(!multipartFile.isEmpty()){
            String image = awsS3Service.uploadFile(multipartFile);
            memberDto.setImage(image);
        }

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
