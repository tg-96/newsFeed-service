package com.newsfeed.repository;

import com.newsfeed.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long>, MemberRepositoryCustom {
    Optional<Member> findByEmail(String email);
    Optional<Member> findById(Long memberId);
}
