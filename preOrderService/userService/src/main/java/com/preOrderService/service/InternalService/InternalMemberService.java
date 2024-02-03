package com.preOrderService.service.InternalService;

import com.preOrderService.adaptor.MemberAdaptor;
import com.preOrderService.config.jwt.JWTUtil;
import com.preOrderService.entity.Member;
import com.preOrderService.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InternalMemberService {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    JWTUtil jwtUtil;

    /**
     * member 한명의 정보
     */
    public MemberAdaptor getMember(String token) {
        String email = jwtUtil.getUsername(token);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("멤버 정보가 없습니다."));

        return new MemberAdaptor(
                member.getId(), member.getEmail(), member.getPassword(), member.getImage(), member.getIntroduction()
        );
    }

    /**
     * 이메일에 해당하는 멤버 정보 조회
     */
    public MemberAdaptor getMemberByEmail(String token, String email){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("멤버 정보가 없습니다."));

        return new MemberAdaptor(
                member.getId(), member.getEmail(), member.getPassword(), member.getImage(), member.getIntroduction()
        );
    }

    /**
     * memberId로 멤버 조회
     */
    public MemberAdaptor getMemberById(String token, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("멤버 정보가 없습니다."));

        return new MemberAdaptor(
                member.getId(), member.getEmail(), member.getPassword(), member.getImage(), member.getIntroduction()
        );
    }
}
