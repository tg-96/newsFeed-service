package com.newsfeed.repository;

import com.newsfeed.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    @Query("select pl from PostLikes pl where pl.posts.id = :postId")
    Optional<PostLikes> findByPostId(@Param("postId") Long postId);
}
