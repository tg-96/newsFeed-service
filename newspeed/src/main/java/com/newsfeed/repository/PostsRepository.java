package com.newsfeed.repository;

import com.newsfeed.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query
    List<Posts> findPostsByWriterId(Long writerId);
}
