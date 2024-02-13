package com.preOrderService.service;

import com.preOrderService.dto.CommentLikesDto;
import com.preOrderService.dto.PostLikesDto;
import com.preOrderService.dto.PostsDto;
import com.preOrderService.dto.request.RequestCommentsDto;
import com.preOrderService.dto.response.CommentsResponseDto;
import com.preOrderService.dto.response.ResponseActivitiesDto;
import com.preOrderService.dto.response.ResponsePostDto;
import com.preOrderService.entity.*;
import com.preOrderService.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO: 알림 추가하는 부분 반복. 따로 메서드로 빼야함.
 */
@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivitiesRepository activitiesRepository;
    private final PostsRepository postsRepository;
    private final FollowsRepository followsRepository;
    private final NotificationService notificationService;
    private final ToFeedService toFeedService;
    private final CommentsRepository commentsRepository;
    private final PostLikesRepository postLikesRepository;
    private final CommentLikesRepository commentLikesRepository;

    /**
     * 포스트 조회
     */
    public ResponsePostDto getPost(Long postId) {
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new RuntimeException("포스트가 존재하지 않습니다."));
        return ResponsePostDto.builder()
                .text(post.getContent())
                .writerId(post.getWriterId())
                .image(post.getImage())
                .createDate(post.getCreatedDate())
                .updateDate(post.getModifiedDate())
                .build();
    }

    /**
     * 나의 알림 조회
     */
    public List<ResponseActivitiesDto> findActivities(Long memberId) {
        List<Activities> activities = activitiesRepository.findByOwnerId(memberId); // integer -> Long 타입으로 변환
        return activities.stream().map(activity -> new ResponseActivitiesDto(activity.getNotification())).collect(Collectors.toList());
    }


    /**
     * 팔로우 & 언팔로우
     * 팔로우 중이 아니면 팔로우하고,
     * 팔로우 중이면 언팔로우 한다.
     */
    @Transactional
    public void changeFollow(String token, Long fromMemberId, Long toMemberId) {
        //follow 관계 불러옴
        Optional<Follows> relation = followsRepository.isFollow(fromMemberId, toMemberId);

        //팔로우 상대면 팔로우 취소
        if (relation.isPresent()) {
            followsRepository.delete(relation.get());
        }

        //팔로우 상태 아니면 팔로우
        else {
            //팔로우
            followsRepository.save(new Follows(fromMemberId, toMemberId));

            //팔로워 조회
            List<Long> followerIdList = followsRepository.findFollowerList(fromMemberId);

            //팔로워들의 알림에 추가
            followerIdList.stream().filter(id -> !id.equals(toMemberId)).forEach(id -> {
                notificationService.addActToFollower(token, id, fromMemberId, toMemberId, ActivityType.FOLLOWS);
            });

            //작성자 알림에 추가
            notificationService.addActToWriter(token, toMemberId, fromMemberId, ActivityType.FOLLOWS);
        }
    }

    /**
     * 포스트 작성 -> 내 피드, 포스트에 추가,팔로워들의 피드에 포스트 추가
     */
    @Transactional
    public void writePost(String token, Long memberId, PostsDto postsDto, String image) {
        //게시물 생성
        Posts post = new Posts(postsDto.getContent(), image, memberId);
        Posts saved_posts = postsRepository.save(post);

        //내피드에 추가
        toFeedService.addToFeed(token, memberId, saved_posts.getId());

        /**
         * TODO
         */
        //내 팔로워들의 피드에 추가
        List<Long> followerIdList = followsRepository.findFollowerList(memberId);
        if (followerIdList != null) {
            followerIdList.stream().forEach(followerId -> {
                toFeedService.addToFeed(token, followerId, saved_posts.getId());

                //내 팔로워들의 알림에 추가
                notificationService.addActToFollower(token, followerId, memberId, null, ActivityType.POSTS);
            });
        }
    }

    /**
     * 댓글 작성
     */
    @Transactional
    public void writeComments(String token, Long writerId, RequestCommentsDto req) {
        //댓글 추가
        Comments comments = new Comments(writerId, req.getPostId(), req.getText());
        Comments save = commentsRepository.save(comments);

        //post 조회
        Posts post = postsRepository.findById(req.getPostId()).orElseThrow(() -> new RuntimeException("조회된 post가 없습니다."));

        //작성자 id
        Long toMemberId = post.getWriterId();

        // 팔로워 조회
        List<Long> followerList = followsRepository.findFollowerList(writerId);

        //팔로워들의 알림에 추가
        followerList.stream().filter(id -> !id.equals(toMemberId)).forEach(id -> {
            notificationService.addActToFollower(token, id, writerId, toMemberId, ActivityType.COMMENTS);
        });

        //작성자의 알림에 추가, 자기 자신의 글에 댓글 단 경우는 제외
        if (!writerId.equals(toMemberId)) {
            notificationService.addActToWriter(token, toMemberId, writerId, ActivityType.COMMENTS);
        }
    }

    /**
     * 댓글 조회
     */
    public List<CommentsResponseDto> getComments(Long postId) {
        List<Comments> comments = commentsRepository.findByPostId(postId);

        return comments.stream().map(comment -> new CommentsResponseDto(comment.getId(), comment.getWriterId(), comment.getText())).collect(Collectors.toList());
    }

    /**
     * 게시글 좋아요
     */
    @Transactional
    public void postLike(String token, Long fromMemberId, Long postId) {
        //post 작성자 조회
        Posts post = postsRepository.findById(postId).orElseThrow(() -> new RuntimeException("좋아요한 게시글이 존재하지 않습니다."));
        Long toMemberId = post.getWriterId();

        //좋아요 저장
        PostLikes like = new PostLikes(postId, fromMemberId);
        postLikesRepository.save(like);


        //팔로워에 알림 남기기
        List<Long> followerList = followsRepository.findFollowerList(fromMemberId);

        followerList.stream().filter(id -> !id.equals(toMemberId)).forEach(id -> {
            notificationService.addActToFollower(token, id, fromMemberId, toMemberId, ActivityType.POST_LIKES);
        });

        //게시글 주인 활동에 남기기, 내 게시물에 좋아요 눌렀을 때는 제외
        if (!toMemberId.equals(fromMemberId)) {
            notificationService.addActToWriter(token, toMemberId, fromMemberId, ActivityType.POST_LIKES);
        }
    }

    /**
     * 게시글 좋아요 조회
     */
    public List<PostLikesDto> findPostLike(Long postId) {
        Set<PostLikes> postLikesList = postLikesRepository.findByPostId(postId);

        return postLikesList.stream().map(pl -> new PostLikesDto(postId, pl.getActorId())).collect(Collectors.toList());
    }

    /**
     * 댓글 좋아요
     */
    @Transactional
    public void commentLike(String token, Long commentId, Long fromMemberId) {
        //좋아요한 댓글 조회
        Comments comment = commentsRepository.findById(commentId).orElseThrow(() -> new RuntimeException("좋아요한 댓글이 존재하지 않습니다."));

        //댓글 작성자 조회
        Long writerId = comment.getWriterId();
        //댓글 좋아요 저장
        CommentLikes commentLikes = new CommentLikes(commentId, fromMemberId);
        commentLikesRepository.save(commentLikes);

        //팔로워의 알림에 추가
        List<Long> followerList = followsRepository.findFollowerList(fromMemberId);

        //작성자가 팔로워일 경우는 제외하고 알림 추가
        followerList.stream().filter(id -> !id.equals(writerId)).forEach(id -> {
            notificationService.addActToFollower(token, id, fromMemberId, writerId, ActivityType.COMMENT_LIKE);
        });

        //작성자의 알림에 남기기, 내 댓글에 좋아요 누른 경우는 제외
        if (!writerId.equals(fromMemberId)) {
            notificationService.addActToWriter(token, writerId, fromMemberId, ActivityType.COMMENT_LIKE);
        }
    }

    /**
     * 댓글 좋아요 조회
     */
    public List<CommentLikesDto> findCommentLike(Long commentId) {
        List<CommentLikes> commentLikesList = commentLikesRepository.findCommentLikesByCommentsId(commentId);

        return commentLikesList.stream().map(cl -> new CommentLikesDto(commentId, cl.getActorId())).collect(Collectors.toList());
    }

}
