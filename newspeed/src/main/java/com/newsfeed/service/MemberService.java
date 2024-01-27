package com.newsfeed.service;

import com.newsfeed.dto.MemberDto;
import com.newsfeed.entity.Member;
import com.newsfeed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public void updateProfile(MemberDto memberDto){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(name).get();
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
    public void updatePassword(String password){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        Member member = memberRepository.findByEmail(name).get();
        member.changePassword(password);
    }
}
