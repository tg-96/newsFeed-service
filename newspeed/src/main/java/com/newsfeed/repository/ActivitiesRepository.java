package com.newsfeed.repository;

import com.newsfeed.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ActivitiesRepository extends JpaRepository<Activities,Long> {
    @Query("select a from Activities a where a.owner.id = :id")
    List<Activities> findByOwnerId(@Param("id") Long id);
}
