package com.preOrderService.repository;

import com.preOrderService.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Set;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
    @Query("select pl from PostLikes pl where pl.postId = :postId")
    Set<PostLikes> findByPostId(@Param("postId") Long postId);
}
