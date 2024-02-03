package com.preOrderService.service;

import com.preOrderService.dto.*;
import com.preOrderService.entity.*;
import com.preOrderService.newsFeed.dto.*;
import com.preOrderService.newsFeed.entity.*;
import com.preOrderService.newsFeed.repository.*;
import com.preOrderService.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsFeedService {
    private final FollowsRepository followRepository;
    private final FeedsRepository feedsRepository;
    private final PostsRepository postsRepository;
    private final CommentsRepository commentsRepository;
    private final PostLikesRepository postLikesRepository;
    private final CommentLikesRepository commentLikesRepository;
    private final ToMemberService toMemberService;
    private final ToActivityService toActivityService;

    /**
     * 팔로우 & 언팔로우
     * 팔로우 중이 아니면 팔로우하고,
     * 팔로우 중이면 언팔로우 한다.
     */
    @Transactional
    public void changeFollow(String token, Long fromMemberId, Long toMemberId) {
        //follow 관계 불러옴
        Optional<Follows> relation = followRepository.isFollow(fromMemberId, toMemberId);

        //팔로우 상대면 팔로우 취소
        if (relation.isPresent()) {
            followRepository.delete(relation.get());
        }

        //팔로우 상태 아니면 팔로우
        else {
            //팔로우
            followRepository.save(new Follows(fromMemberId, toMemberId));

            //팔로워 조회
            List<Long> followerIdList = followRepository.findFollowerList(fromMemberId);
            //멤버 이름 조회
            String fromMemberName = toMemberService.getMemberNameById(token, fromMemberId);
            String toMemberName = toMemberService.getMemberNameById(token, toMemberId);
            toActivityService.addActivities(token, followerIdList, fromMemberName, toMemberName, "FOLLOWS");
        }
    }
    /**
     * 팔로우 조회
     */

    /**
     * 팔로워 조회
     */

    /**
     * 뉴스피드 조회
     */
    public List<FeedsDto> getFeeds(Long memberId) {
        // 피드 조회
        List<Feeds> feeds = feedsRepository.findByOwner(memberId);

        //feeds가 없으면 예외 처리
        if (feeds.isEmpty()) {
            throw new RuntimeException("피드가 비어있습니다.");
        }

        //feed와 1대1 매핑된 post들의 정보를 가져옴
        List<Posts> posts = feeds.stream().map(f -> f.getPost()).collect(Collectors.toList());

        return posts.stream()
                .map(post ->
                        new FeedsDto(
                                post.getContent(),
                                post.getWriteMemberId(),
                                post.getImage(),
                                post.getCreatedDate(),
                                post.getModifiedDate())
                ).collect(Collectors.toList());
    }

    /**
     * 포스트 작성 -> 내 피드, 포스트에 추가,팔로워들의 피드에 포스트 추가
     */
    @Transactional
    public void writePost(String token, Long memberId, PostsDto postsDto) {
        //게시물 생성
        Posts post = new Posts(postsDto.getContent(), postsDto.getImage(), memberId);
        Posts saved_posts = postsRepository.save(post);

        //내피드에 추가
        Feeds feed = new Feeds(saved_posts, memberId);
        feedsRepository.save(feed);

        /**
         * bulk 쿼리연산으로 수정
         */
        //내 팔로워들의 피드에 추가
        List<Long> followerIdList = followRepository.findFollowerList(memberId);
        followerIdList.stream().forEach(id -> {
            feedsRepository.save(new Feeds(saved_posts, id));

            //현재 로그인한 멤버 이메일
            String fromMemberName = toMemberService.getMemberNameById(token, memberId);

            //내 팔로워들의 활동에 추가
            toActivityService.addActivities(token, followerIdList, fromMemberName, null, "POSTS");
        });
    }

        /**
         * 댓글 작성
         */
        @Transactional
        public Long writeComments(String token, Long fromMemberId, Long postId, CommentsDto commentsDto){
            //게시물 조회
            Optional<Posts> post = postsRepository.findById(postId);
            if (post.isEmpty()) {
                throw new RuntimeException("게시글이 존재하지 않습니다.");
            }

            //댓글 추가
            Comments comments = new Comments(fromMemberId, post.get(), commentsDto.getText());
            Comments save = commentsRepository.save(comments);

            // 팔로워 조회
            List<Long> followerList = followRepository.findFollowerList(fromMemberId);

            //작성자 이름
            String fromMemberName = toMemberService.getMemberNameById(token,fromMemberId);

            //게시글 주인 이름
            Long toMemberId = post.get().getWriteMemberId();
            String toMemberName = toMemberService.getMemberNameById(token,toMemberId);

            //팔로워들의 활동에 추가
            toActivityService.addActivities(token,followerList,fromMemberName,toMemberName,"COMMENTS");

            //게시물 주인의 활동에 추가
            toActivityService.addActivityToOwner(token,toMemberId,fromMemberName,"COMMENTS");
            return save.getId();
        }

        /**
         * 댓글 조회
         */
        public List<CommentsResponseDto> findCommentsByPost (Long postId){
            List<Comments> comments = commentsRepository.findByPostId(postId);

            return comments.stream().map(c ->
                    new CommentsResponseDto(c.getId(), c.getWriteMemberId(), c.getText())
            ).collect(Collectors.toList());
        }

        /**
         * 게시글 좋아요
         */
        @Transactional
        public void postLike (String token,Long memberId, Long postId){
            Optional<Posts> post = postsRepository.findById(postId);
            if (post.isEmpty()) {
                throw new RuntimeException("좋아요한 게시글이 존재하지 않습니다.");
            }

            //좋아요 저장
            PostLikes postLikes = new PostLikes(post.get(), memberId);
            postLikesRepository.save(postLikes);

            //좋아요 활동 팔로워에 남기기
            List<Long> followerList = followRepository.findFollowerList(memberId);
            String fromUserName = toMemberService.getMemberNameById(token,memberId);
            String toUserName = toMemberService.getMemberNameById(token,post.get().getWriteMemberId());
            toActivityService.addActivities(token,followerList,fromUserName,toUserName,"POST_LIKES");

            //게시글 주인 활동에 남기기
            toActivityService.addActivityToOwner(token,post.get().getWriteMemberId(),fromUserName,"POST_LIKES");
        }

        /**
         * 게시글 좋아요 조회
         */
        public List<PostLikesDto> findPostLike (Long postId){
            List<PostLikes> postLikesList = postLikesRepository.findByPostId(postId);

            return postLikesList.stream()
                    .map(pl -> new PostLikesDto(postId, pl.getLikeMemberId()))
                    .collect(Collectors.toList());
        }

        /**
         * 댓글 좋아요
         */
        @Transactional
        public Long commentLike (Long commentsId, String email){
            Optional<Comments> comment = commentsRepository.findById(commentsId);
            if (comment.isEmpty()) {
                throw new RuntimeException("좋아요한 댓글이 존재하지 않습니다.");
            }
            Optional<Member> member = memberRepository.findByEmail(email);
            if (member.isEmpty()) {
                throw new RuntimeException("좋아요한 회원이 존재하지 않습니다.");
            }

            //댓글 좋아요 저장
            CommentLikes commentLikes = new CommentLikes(comment.get(), member.get());
            CommentLikes save = commentLikesRepository.save(commentLikes);

            //댓글 좋아요, 팔로워 활동에 남기기
            List<Long> followerList = followRepository.findFollowerList(member.get().getId());
            followerList.stream().forEach(id ->
                    memberRepository.findById(id).ifPresent(m -> {
                        Activities activities = new Activities(m, ActivityType.COMMENT_LIKE, email, comment.get().getWriteMemberId().getEmail());
                        activitiesRepository.save(activities);
                    })
            );

            //댓글 주인의 활동에 남기기
            Activities activities = new Activities(comment.get().getWriteMemberId(), ActivityType.COMMENT_LIKE, email, comment.get().getWriteMemberId().getEmail());
            String notification = email + "님이 내 댓글을 좋아합니다.";
            activitiesRepository.save(activities);

            return save.getId();
        }

        /**
         * 댓글 좋아요 조회
         */
        public List<CommentLikesDto> findCommentLike (Long commentId){
            List<CommentLikes> commentLikesList = commentLikesRepository.findCommentLikesByCommentsId(commentId);
            Optional<Comments> comments = commentsRepository.findById(commentId);

            int count = commentLikesList.size();

            return commentLikesList.stream()
                    .map(cl -> new CommentLikesDto(commentId, comments.get().getWriteMemberId().getEmail(), count))
                    .collect(Collectors.toList());
        }


    }
