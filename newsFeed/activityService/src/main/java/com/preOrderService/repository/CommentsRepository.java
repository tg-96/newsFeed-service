package com.preOrderService.repository;

import com.preOrderService.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentsRepository extends JpaRepository<Comments,Long> {
    @Query("select c from Comments c where c.postId = :postId order by c.createdDate DESC ")
    List<Comments> findByPostId(@Param("postId")Long postId);
}
