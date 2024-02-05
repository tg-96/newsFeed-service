package com.preOrderService.repository;

import com.preOrderService.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("select p from Posts p where p.writeMemberId = :writerId order by p.createdDate desc")
    List<Posts> findPostsByWriterId(@Param("writerId") Long writerId);
}
