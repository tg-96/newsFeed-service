package com.newsfeed.repository;

import com.newsfeed.entity.Feeds;
import com.newsfeed.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedsRepository extends JpaRepository<Feeds,Long> {
    public List<Feeds> findByOwner(Member member);
}
