package com.newsfeed.repository;

import com.newsfeed.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follows,Long> {
    // 팔로우 관계가 있는지 조회
    @Query("select f from Follows f where f.fromUser = :from and f.toUser = :to")
    Optional<Follows> isFollow(@Param("from")Long fromUserId, @Param("to") Long toUserId);
    // 회원의 팔로우 리스트 조회
    @Query("select f.toUser from Follows f where f.fromUser = :fromUser")
    List<Long> findFollowLists(@Param("fromUser")Long fromUser);
}
