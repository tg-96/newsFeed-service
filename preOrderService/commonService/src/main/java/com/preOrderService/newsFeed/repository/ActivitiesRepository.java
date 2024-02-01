package com.preOrderService.newsFeed.repository;

import com.preOrderService.newsFeed.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivitiesRepository extends JpaRepository<Activities,Long> {
    @Query("select a from Activities a where a.owner.id = :id order by a.createdDate desc")
    List<Activities> findByOwnerId(@Param("id") Long id);
}
