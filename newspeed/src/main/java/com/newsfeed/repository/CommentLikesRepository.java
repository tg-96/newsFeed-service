package com.newsfeed.repository;

import com.newsfeed.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    @Query("select cl from CommentLikes cl where cl.likeUsersEmail = :email")
    CommentLikes findCommentLikesByLikeUsersEmail(@Param("email") String email);

}
