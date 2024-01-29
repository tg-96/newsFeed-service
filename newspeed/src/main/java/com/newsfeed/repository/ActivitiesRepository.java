package com.newsfeed.repository;

import com.newsfeed.entity.Activities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivitiesRepository extends JpaRepository<Activities,Long> {
}
