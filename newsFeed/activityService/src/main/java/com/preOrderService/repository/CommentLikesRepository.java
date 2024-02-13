package com.preOrderService.repository;

import com.preOrderService.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    @Query("select cl from CommentLikes cl where cl.commentId = :commentId")
    List<CommentLikes> findCommentLikesByCommentsId(@Param("commentId") Long commentId);
}
