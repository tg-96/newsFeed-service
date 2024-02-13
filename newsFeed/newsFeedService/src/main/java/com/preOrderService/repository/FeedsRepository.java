package com.preOrderService.repository;

import com.preOrderService.entity.Feeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedsRepository extends JpaRepository<Feeds, Long> {
    @Query("select f from Feeds f where f.ownerId = :memberId order by f.createdDate desc")
    List<Feeds> findByOwner(@Param("memberId") Long memberId);

    @Query("select f.postId from Feeds f where f.ownerId = :ownerId order by f.createdDate desc")
    List<Long> findPostIdList(@Param("ownerId")Long ownerId);
}
