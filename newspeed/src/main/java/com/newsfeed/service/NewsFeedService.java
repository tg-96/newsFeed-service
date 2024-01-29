package com.newsfeed.service;

import com.newsfeed.dto.FeedsDto;
import com.newsfeed.dto.PostsDto;
import com.newsfeed.entity.*;
import com.newsfeed.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsFeedService {
    private final FollowsRepository followRepository;
    private final MemberRepository memberRepository;
    private final ActivitiesRepository activitiesRepository;
    private final FeedsRepository feedsRepository;
    private final PostsRepository postsRepository;

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
        if (feeds.isEmpty()) {
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
        Optional<Member> member = memberRepository.findByEmail(writerEmail);

        if (member.isEmpty()) {
            throw new RuntimeException("멤버 정보가 없습니다.");
        }

        //게시물 생성
        Posts post = new Posts(postsDto.getContent(), postsDto.getImage(), member.get());
        Posts saved_posts = postsRepository.save(post);

        //내피드에 추가
        Feeds feed = new Feeds(saved_posts, member.get());
        feedsRepository.save(feed);

        //내 활동에 추가
        Activities activity = Activities.builder()
                .member(member.get())
                .actorEmail(writerEmail)
                .type(ActivityType.POSTS)
                .build();

        activitiesRepository.save(activity);

        //내 팔로워들의 피드,활동에 추가
        List<Long> followerIdList = followRepository.findFollowerList(member.get().getId());

        followerIdList.stream().forEach(id -> {
            Optional<Member> follower = memberRepository.findById(id);

            if (follower.isEmpty()) {
                throw new RuntimeException("follower 정보가 존재하지 않습니다.");
            }

            feedsRepository.save(new Feeds(saved_posts, follower.get()));

            activitiesRepository.save(Activities.builder()
                    .member(follower.get())
                    .type(ActivityType.POSTS)
                    .actorEmail(writerEmail)
                    .build());
        });
    }
}
