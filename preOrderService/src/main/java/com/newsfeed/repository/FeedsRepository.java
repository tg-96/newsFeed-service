package com.newsfeed.repository;

import com.newsfeed.entity.Feeds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeedsRepository extends JpaRepository<Feeds, Long> {
    @Query("select f from Feeds f where f.owner.id = :memberId order by f.createdDate desc")
    List<Feeds> findByOwner(@Param("memberId") Long memberId);
}
