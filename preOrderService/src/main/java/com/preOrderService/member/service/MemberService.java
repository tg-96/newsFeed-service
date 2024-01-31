package com.preOrderService.member.service;

import com.preOrderService.member.dto.JoinDto;
import com.preOrderService.newsFeed.dto.MemberDto;
import com.preOrderService.member.entity.Member;
import com.preOrderService.newsFeed.repository.FollowsRepository;
import com.preOrderService.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final FollowsRepository followRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public MemberDto findMember(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            throw new RuntimeException("조회한 멤버 정보가 없습니다.");
        }
        return new MemberDto(member.get().getName(),member.get().getImage(),member.get().getIntroduction());
    }
    @Transactional
    public void updateProfile(MemberDto memberDto,String email){
        Member member = memberRepository.findByEmail(email).get();
        if(memberDto.getImage() != null){
            member.changeImg(memberDto.getImage());
        }
        if(memberDto.getIntroduction() != null){
            member.changeIntroduction(memberDto.getIntroduction());
        }
        if(memberDto.getName() != null){
            member.changeName(memberDto.getName());
        }
    }
    @Transactional
    public void updatePassword(String password,String email){
        Member member = memberRepository.findByEmail(email).get();
        member.changePassword(bCryptPasswordEncoder.encode(password));
    }

    public Member findMemberByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            throw new RuntimeException("해당 이메일로 등록된 회원이 없습니다.");
        }
        return member.get();
    }

    @Transactional
    public void join(JoinDto joinDto) {
        String name = joinDto.getName();
        String email = joinDto.getEmail();
        String password = joinDto.getPassword();
        String role = joinDto.getRole();

        Optional<Member> member = memberRepository.findByEmail(email);
        System.out.println(member);
        // 가입한 적이 있는 이메일이면
        if (!member.isEmpty()) {
            throw new RuntimeException("가입 오류: 이미 가입한 적이 있는 이메일 입니다.");
        }
        // 가입 진행
        Member newMember = new Member(name, email, bCryptPasswordEncoder.encode(password),role);
        memberRepository.save(newMember);
    }


}
