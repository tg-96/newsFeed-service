package com.newsfeed.repository;

import com.newsfeed.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    // 멤버 정보만 가져올 떄
    Optional<Member> findByEmail(String email);
    // member.feeds도 조회하려고 할때, fetch join으로 N+1 해결
    // 피드 최신순으로 정렬
    @Query("select m from Member m join fetch m.feeds f where m.email = :email order by f.createdDate")
    Optional<Member> findByEmailJoinFeeds(@Param("email") String email);
    @Query("select m from Member m join fetch m.feeds where m.id = :id")
    Optional<Member> findByIdJoinFeeds(@Param("id") Long id);

    // 포스트 작성했을때 -> 내가 쓴 게시물, 내 피드, 내 활동 추가
    @Query("select m from Member m "+
            "join fetch m.posts "+
            "join fetch m.feeds "+
            "join fetch m.activities "+
            "where m.email = :email")
    Optional<Member> findByEmailJoinPostsAndFeedsAndActivities(@Param("email") String email);

    // 포스트 작성했을때 -> 내 팔로워들의 피드,활동에 추가
    @Query("select m from Member m "+
            "join fetch m.feeds "+
            "join fetch m.activities "+
            "where m.id = :id")
    Optional<Member> findByIdJoinFeedAndActivities(@Param("id") Long id);

    // 팔로우 했을때 -> 팔로워들의 활동 목록에 추가
    @Query("select m from Member m join fetch m.activities where m.id = :id")
    Optional<Member> findByIdJoinActivities(@Param("id")Long id);

    Optional<Member> findById(Long memberId);

}
