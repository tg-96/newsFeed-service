package com.preOrderService.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ActivitiesTest {
    @Test
    void 활동_생성(){
        //given
        Activities activities_posts = new Activities(1L,ActivityType.POSTS,"손흥민","황희찬");
        Activities activities_post_likes = new Activities(1L,ActivityType.POST_LIKES,"손흥민","황희찬");
        Activities activities_comments = new Activities(1L,ActivityType.COMMENTS,"손흥민","황희찬");
        Activities activities_comment_like = new Activities(1L,ActivityType.COMMENT_LIKE,"손흥민","황희찬");
        Activities activities_follows = new Activities(1L,ActivityType.FOLLOWS,"손흥민","황희찬");
        //when,then
        assertThat(activities_follows.getNotification()).isEqualTo("손흥민님이 황희찬님을 팔로우 했습니다.");
        assertThat(activities_comment_like.getNotification()).isEqualTo("손흥민님이 황희찬님의 댓글을 좋아합니다.");
        assertThat(activities_comments.getNotification()).isEqualTo("손흥민님이 황희찬님의 글에 댓글을 작성했습니다.");
        assertThat(activities_post_likes.getNotification()).isEqualTo("손흥민님이 황희찬님의 글을 좋아합니다.");
        assertThat(activities_posts.getNotification()).isEqualTo("손흥민님이 게시물을 작성 했습니다.");
    }
}