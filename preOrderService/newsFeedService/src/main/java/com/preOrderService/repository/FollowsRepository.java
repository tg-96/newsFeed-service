package com.preOrderService.repository;

import com.preOrderService.entity.Follows;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowsRepository extends JpaRepository<Follows,Long> {
    // 팔로우 관계가 있는지 조회
    @Query("select f from Follows f where f.fromUserId = :from and f.toUserId = :toUser")
    Optional<Follows> isFollow(@Param("from")Long fromUserId, @Param("toUser") Long toUserId);

    // 회원의 팔로우 리스트 조회
    @Query("select f.toUserId from Follows f where f.fromUserId = :fromUser")
    List<Long> findFollowLists(@Param("fromUser")Long fromUserId);

    // 나를 팔로우 하고 있는 회원 조회
    @Query("select f.fromUserId from Follows f where f.toUserId = :toUser")
    List<Long> findFollowerList(@Param("toUser")Long toUserId);
}
