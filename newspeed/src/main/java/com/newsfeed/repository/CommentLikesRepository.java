package com.newsfeed.repository;

import com.newsfeed.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikesRepository extends JpaRepository<CommentLikes,Long> {


}
