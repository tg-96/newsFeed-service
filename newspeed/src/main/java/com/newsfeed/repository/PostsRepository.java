package com.newsfeed.repository;

import com.newsfeed.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostsRepository extends JpaRepository<Posts,Long> {
    @Query("select p from Posts p where p.writer.id = :writerId order by p.createdDate desc")
    List<Posts> findPostsByWriterId(@Param("writer_id") Long writerId);
}
