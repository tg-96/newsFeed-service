package com.preOrderService.newsFeed.repository;

import com.preOrderService.newsFeed.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    @Query("select pl from PostLikes pl where pl.posts.id = :postId")
    List<PostLikes> findByPostId(@Param("postId") Long postId);
}
