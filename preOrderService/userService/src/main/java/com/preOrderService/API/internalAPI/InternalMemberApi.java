package com.preOrderService.API.internalAPI;

import com.preOrderService.adaptor.MemberAdaptor;
import com.preOrderService.service.InternalService.InternalMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalMemberApi {
    private final InternalMemberService internalMemberService;

    /**
     * 현재 로그인하고 있는 멤버 정보
     */
    @GetMapping("/member")
    public MemberAdaptor getMember(@RequestHeader("Authorization")String token){
        return internalMemberService.getMember(token);
    }

    /**
     * 이메일로 멤버 정보 조회
     */
    @GetMapping("/member/{email}")
    public MemberAdaptor getMember(@RequestHeader("Authorization")String token, String email){
        internalMemberService.
    }

}
