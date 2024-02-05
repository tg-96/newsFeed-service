package com.preOrderService.api.internal;

import com.preOrderService.dto.MemberResponseDto;
import com.preOrderService.dto.MemberResponseNameDto;
import com.preOrderService.service.MemberService;
import com.preOrderService.service.InternalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class InternalMemberController {
    private final InternalMemberService internalMemberService;
    private final MemberService memberService;

    /**
     * memberID로 멤버 정보 조회
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
