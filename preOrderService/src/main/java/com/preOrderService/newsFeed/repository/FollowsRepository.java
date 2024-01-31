package com.preOrderService.newsFeed.repository;

import com.preOrderService.newsFeed.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowsRepository extends JpaRepository<Follows,Long> {
    // 팔로우 관계가 있는지 조회
    @Query("select f from Follows f where f.fromUser = :from and f.toUser = :toUser")
    Optional<Follows> isFollow(@Param("from")Long fromUserId, @Param("toUser") Long toUserId);

    // 회원의 팔로우 리스트 조회
    @Query("select f.toUser from Follows f where f.fromUser = :fromUser")
    List<Long> findFollowLists(@Param("fromUser")Long fromUserId);

    // 나를 팔로우 하고 있는 회원 조회
    @Query("select f.fromUser from Follows f where f.toUser = :toUser")
    List<Long> findFollowerList(@Param("toUser")Long toUserId);
}
