package com.preOrderService.API;

import com.preOrderService.dto.*;
import com.preOrderService.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class NewsFeedController {
    private final NewsFeedService newsFeedService;

    /**
     * 팔로우
     */
    @PostMapping("/follow/{email}")
    public ResponseEntity<Void> follow(@PathVariable("email") String toEmail,@RequestHeader("Authorization")String token) {
        newsFeedService.changeFollow(toEmail,token);

        return ResponseEntity.ok().build();
    }

    /**
     * 피드 조회
     */
    @GetMapping
    public List<FeedsDto> getNewsFeeds(@RequestHeader("Authorization")String token) {
        //뉴스피드 조회
        List<FeedsDto> newsFeeds = newsFeedService.getFeeds(token);

        return newsFeeds;
    }



    /**
     * 게시글 작성
     */
    @PostMapping("/posts")
    public ResponseEntity<Void> writePost(@RequestBody PostsDto postsDto) {
        String email = SecurityContextHolde69r.getContext().getAuthentication().getName();
        newsFeedService.writePost(email, postsDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 작성
     */
    @PostMapping("/comments/{postId}")
    public ResponseEntity<Void> writeComments(@PathVariable("postId") Long postId,
                                              @RequestBody CommentsDto commentsDto) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.writeComments(email, postId, commentsDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 조회
     */
    @GetMapping("/comments/{postId}")
    public List<CommentsResponseDto> findComments(@PathVariable("postId") Long postId) {
        return newsFeedService.findCommentsByPost(postId);
    }

    /**
     * 게시글 좋아요
     */
    @PostMapping("/posts/like/{postId}")
    public ResponseEntity<Void> postLike(@PathVariable("postId") Long postId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.postLike(email, postId);
        return ResponseEntity.ok().build();
    }

    /**
     * 게시글별 좋아요 조회
     */
    @GetMapping("/posts/like/{postId}")
    public List<PostLikesDto> findPostLikes(@PathVariable("postId") Long postId) {
        return newsFeedService.findPostLike(postId);
    }

    /**
     * 댓글별 좋아요 조회
     */
    @GetMapping("/comments/like/{commentId}")
    public List<CommentLikesDto> findCommentLikes(@PathVariable("commentId") Long commentId) {
        return newsFeedService.findCommentLike(commentId);
    }

    /**
     * 댓글 좋아요
     */
    @PostMapping("/comments/like/{commentId}")
    public ResponseEntity<Void> commentLike(@PathVariable("commentId") Long commentId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        newsFeedService.commentLike(commentId, email);
        return ResponseEntity.ok().build();
    }

}
