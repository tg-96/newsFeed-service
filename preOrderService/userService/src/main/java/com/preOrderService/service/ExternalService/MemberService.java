package com.preOrderService.service.ExternalService;

import com.preOrderService.entity.Member;
import com.preOrderService.dto.JoinDto;
import com.preOrderService.dto.MemberDto;
import com.preOrderService.repository.MemberRepository;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public MemberDto findMember(Long userId){
        Optional<Member> member = memberRepository.findById(userId);
        if(member.isEmpty()){
            throw new RuntimeException("조회한 멤버 정보가 없습니다.");
        }
        return new MemberDto(member.get().getName(),member.get().getImage(),member.get().getIntroduction());
    }
    @Transactional
    public void updateProfile(MemberDto memberDto,Long userId){
        Member member = memberRepository.findById(userId).get();
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
    public void updatePassword(String password,Long id){
        Member member = memberRepository.findById(id).get();
        System.out.println("member = " + member);
        member.changePassword(bCryptPasswordEncoder.encode(password));
    }

    public Member findMemberByEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            throw new RuntimeException("해당 이메일로 등록된 회원이 없습니다.");
        }
        return member.get();
    }
    public String findMemberNameById(Long memberId){
        Optional<Member> member = memberRepository.findById(memberId);
        if(member.isEmpty()){
            throw new RuntimeException("회원 정보가 존재하지 않습니다.");
        }
        return member.get().getName();
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
        Member newMember = new Member(null, name, email, bCryptPasswordEncoder.encode(password), role);
        memberRepository.save(newMember);
    }


}
