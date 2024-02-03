package com.preOrderService.api.external;

import com.preOrderService.config.JWTUtil;
import com.preOrderService.dto.*;
import com.preOrderService.dto.request.RequestMemberDto;
import com.preOrderService.service.NewsFeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/newsFeed")
@RequiredArgsConstructor
public class ExternalNewsFeedController {
    private final NewsFeedService newsFeedService;
    private final JWTUtil jwtUtil;

    /**
     * 팔로우
     */
    @PostMapping("/follow")
    public ResponseEntity<Void> follow(@RequestBody RequestMemberDto request, @RequestHeader("Authorization") String token) {
        //유효기간 만료확인
        if (jwtUtil.isExpired(token)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }
        Long fromMemberId = jwtUtil.getUserId(token);
        Long toMemberId = request.getMemberId();

        newsFeedService.changeFollow(token, fromMemberId, toMemberId);

        return ResponseEntity.ok().build();
    }

    /**
     * 피드 조회
     */
    @GetMapping
    public List<FeedsDto> getNewsFeeds(@RequestHeader("Authorization") String token) {
        //토큰 유효성 검증
        if (jwtUtil.isExpired(token)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        Long memberId = jwtUtil.getUserId(token);

        //뉴스피드 조회
        List<FeedsDto> newsFeeds = newsFeedService.getFeeds(memberId);

        return newsFeeds;
    }


    /**
     * 게시글 작성
     */
    @PostMapping("/posts")
    public ResponseEntity<Void> writePost(@RequestBody PostsDto postsDto, @RequestHeader("Authorization") String token) {
        //토큰 유효성 검증
        if (jwtUtil.isExpired(token)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        Long memberId = jwtUtil.getUserId(token);

        newsFeedService.writePost(token, memberId, postsDto);

        return ResponseEntity.ok().build();
    }

    /**
     * 댓글 작성
     */
    @PostMapping("/comments/{postId}")
    public ResponseEntity<Void> writeComments(@RequestHeader("Authorization") String token,
                                              @PathVariable("postId") Long postId,
                                              @RequestBody CommentsDto commentsDto) {
        if (jwtUtil.isExpired(token)) {
            throw new RuntimeException("토큰이 유효하지 않습니다.");
        }

        Long memberId = jwtUtil.getUserId(token);

        newsFeedService.writeComments(token, memberId, postId, commentsDto);

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
