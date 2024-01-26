package com.newsfeed.repository;

import com.newsfeed.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Iterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
@ActiveProfiles("test")
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Test
    @Rollback(value = false)
    public void 회원가입(){
        Member member = Member.builder()
                .name("태규")
                .email("jtg0528@ajou.ac.kr")
                .password("123")
                .build();
        member.changeRole("ADMIN");
        member.toString();
        memberRepository.save(member);
    }
    @Test
    public void 현재_로그인한_회원(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();

        GrantedAuthority auth = iter.next();
        String authority = auth.getAuthority();
        assertThat(authority).isEqualTo("ADMIN");
    }

}