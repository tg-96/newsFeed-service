package com.newsfeed.service;

import com.newsfeed.dto.FeedsDto;
import com.newsfeed.dto.PostsDto;
import com.newsfeed.entity.*;
import com.newsfeed.repository.ActivitiesRepository;
import com.newsfeed.repository.FeedsRepository;
import com.newsfeed.repository.FollowsRepository;
import com.newsfeed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsFeedService {
    private final FollowsRepository followRepository;
    private final MemberRepository memberRepository;
    private final ActivitiesRepository activitiesRepository;
    private final FeedsRepository feedsRepository;

    /**
     * 팔로우 & 언팔로우
     * 팔로우 중이 아니면 팔로우하고,
     * 팔로우 중이면 언팔로우 한다.
     */
    @Transactional
    public String changeFollow(String fromEmail, String toEmail) {
        //사용자
        Long fromMemberId = memberRepository.findByEmail(fromEmail).get().getId();

        //사용자가 팔로우할 대상
        Long toMemberId = memberRepository.findByEmail(toEmail).get().getId();

        //follow 관계 불러옴
        Optional<Follows> relation = followRepository.isFollow(fromMemberId, toMemberId);

        //팔로우 상대면 팔로우 취소
        if (relation.isPresent()) {
            followRepository.delete(relation.get());
            return fromEmail + " 님이 " + toEmail + " 님을 팔로우 취소 했습니다.";
        }
        //팔로우 상태 아니면 팔로우
        followRepository.save(new Follows(fromMemberId, toMemberId));


        //팔로워들의 활동 목록에 추가
        List<Long> followerList = followRepository.findFollowerList(fromMemberId);
        followerList.stream().forEach(id -> memberRepository.findById(id)
                .ifPresent(m -> {
                    Activities activities = new Activities(m, ActivityType.FOLLOWS, fromEmail, toEmail);
                    activitiesRepository.save(activities);
                })
        );

        return fromEmail + " 님이 " + toEmail + " 님을 팔로우 했습니다.";
    }

    /**
     * 뉴스피드 조회
     */
    @Transactional
    public List<FeedsDto> getFeeds(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        // member 정보가 없으면 예외 처리
        if (member.isEmpty()) {
            throw new RuntimeException("피드를 조회할 멤버 정보가 없습니다.");
        }
        // 피드 조회
        List<Feeds> feeds = feedsRepository.findByOwner(member.get());

        //feeds가 없으면 예외 처리
        if (feeds == null || feeds.isEmpty()) {
            throw new RuntimeException("피드가 비어있습니다.");
        }

        //feed와 1대1 매핑된 post들의 정보를 가져옴
        List<Posts> posts = feeds.stream().map(f -> f.getPost()).collect(Collectors.toList());

        return posts.stream()
                .map(post -> new FeedsDto(
                        post.getContent(),
                        post.getWriter().getEmail(),
                        post.getImage(),
                        post.getCreatedDate(),
                        post.getModifiedDate())
                ).collect(Collectors.toList());
    }

    /**
     * 포스트 작성 -> 내 피드, 포스트에 추가,팔로워들의 피드에 포스트 추가
     */
    @Transactional
    public void writePost(String writerEmail, PostsDto postsDto) {
        Optional<Member> member = memberRepository.findByEmailJoinPostsAndFeedsAndActivities(writerEmail);

        if (member.isEmpty()) {
            throw new RuntimeException("멤버 정보가 없습니다.");
        }

        //게시물 생성
        Set<Comments> comments = new HashSet<>();
        Posts post = new Posts(postsDto.getContent(), postsDto.getImage(), member.get());

        //피드 생성
        Feeds feed = new Feeds(post);

        //활동 생성
        Activities activity = Activities.builder()
                .actorEmail(writerEmail)
                .type(ActivityType.POSTS)
                .build();

        //내가 쓴 포스트에 추가
        member.get().addPost(post);

        //내 피드에 추가
        member.get().addFeed(feed);

        //내 활동에 추가
        member.get().addActivity(activity);

        //팔로워 조회
        List<Long> followerIdList = followRepository.findFollowerList(member.get().getId());
        ///팔로워가 없을 경우
        if (followerIdList.isEmpty()) {
            return;
        }

        //팔로워들의 피드/활동에 추가
        followerIdList.stream().forEach(
                id -> {
                    memberRepository
                            .findByIdJoinFeedAndActivities(id)
                            .ifPresent(m -> {
                                m.addFeed(feed);
                                m.addActivity(activity);
                            });
                });
    }
}
