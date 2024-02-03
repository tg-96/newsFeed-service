package com.preOrderService.api.internal;

import com.preOrderService.dto.MemberResponseDto;
import com.preOrderService.dto.MemberResponseNameDto;
import com.preOrderService.service.ExternalService.MemberService;
import com.preOrderService.service.InternalService.InternalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class InternalMemberController {
    private final InternalMemberService internalMemberService;
    private final MemberService memberService;
    /**
     * 현재 로그인하고 있는 멤버 정보
     */
    @GetMapping
    public MemberResponseDto getMember(@RequestHeader("Authorization")String token){
        return internalMemberService.getMember(token);
    }

    /**
     * 이메일로 멤버 정보 조회
     */
    @GetMapping("/{email}")
    public MemberResponseDto getMemberByEmail(@RequestHeader("Authorization")String token, @PathVariable("email") String email){
        return internalMemberService.getMemberByEmail(token,email);
    }

    /**
     * memberID로 멤버 정보 조회
     */
    /**
     * 이메일로 멤버 정보 조회
     */
    @GetMapping("/{memberId}")
    public MemberResponseDto getMemberById(@RequestHeader("Authorization")String token, @PathVariable("memberId")Long memberId){
        return internalMemberService.getMemberById(token,memberId);
    }
    /**
     * memberId로 이름 조회
     */
    @GetMapping
    public MemberResponseNameDto getMemberNameById(@RequestHeader("Authorization")String token,
                                                   @RequestParam("id")Long memberId){
        String name = memberService.findMemberNameById(memberId);
        MemberResponseNameDto response = new MemberResponseNameDto(name);
        return response;
    }
}
