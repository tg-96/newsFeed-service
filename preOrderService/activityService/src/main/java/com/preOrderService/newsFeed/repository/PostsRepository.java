package com.preOrderService.newsFeed.repository;

import com.preOrderService.newsFeed.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("select p from Posts p where p.writer.id = :writerId order by p.createdDate desc")
    List<Posts> findPostsByWriterId(@Param("writerId") Long writerId);
}
