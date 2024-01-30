package com.newsfeed.controller;

import com.newsfeed.dto.CommentsDto;
import com.newsfeed.dto.CommentsResponseDto;
import com.newsfeed.dto.FeedsDto;
import com.newsfeed.dto.PostsDto;
import com.newsfeed.service.MemberService;
import com.newsfeed.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;
    /**
     * 팔로우
     */
    @GetMapping("/follow/{email}")
    public ResponseEntity<Void> follow(@PathVariable("email") String toEmail){
        //현재 사용자 이메일
        String fromEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        newsFeedService.changeFollow(fromEmail,toEmail);

        return ResponseEntity.ok().build();
    }

    /**
     * 피드 조회
     */
    @GetMapping
    public List<FeedsDto> getNewsFeeds(){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        //뉴스피드 조회
        List<FeedsDto> newsFeeds = newsFeedService.getFeeds(email);

        return newsFeeds;
    }

    /**
     * 게시글 작성
     */
    @PostMapping("/posts")
    public ResponseEntity<Void> writePost(@RequestBody PostsDto postsDto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.writePost(email,postsDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 작성
     */
    @PostMapping("/comments/{postId}")
    public ResponseEntity<Void> writeComments(@PathVariable("postId")Long postId,
                                              @RequestBody CommentsDto commentsDto){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.writeComments(email,postId,commentsDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/comments/{postId}")
    public List<CommentsResponseDto> findComments(@PathVariable("postId")Long postId){
        return newsFeedService.findCommentsByPost(postId);
    }

    /**
     * 게시글 좋아요
     */
    @PostMapping("/posts/like/{postId}")
    public ResponseEntity<Void> likePost(@PathVariable("postId")Long postId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.postLike(email,postId);
        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 좋아요
     */
    @PostMapping("/comments/like/{commentId}")
    public ResponseEntity<Void> likeComment(@PathVariable("commentId")Long commentId){
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.commentLike(commentId,email);
        return ResponseEntity.ok().build();
    }
}
