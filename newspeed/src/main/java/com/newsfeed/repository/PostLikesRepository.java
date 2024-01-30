package com.newsfeed.repository;

import com.newsfeed.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikesRepository extends JpaRepository<PostLikes,Long> {
}
