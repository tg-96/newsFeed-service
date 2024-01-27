package com.newsfeed.service;

import com.newsfeed.entity.Follows;
import com.newsfeed.repository.FollowRepository;
import com.newsfeed.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NewsFeedService {
    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    /**
     * 팔로우 & 언팔로우
     */
    @Transactional
    public String changeFollow(String fromEmail, String toEmail) {
        //fromMemberId
        Long fromMemberId = memberRepository.findByEmail(fromEmail).get().getId();

        //toMemberId
        Long toMemberId = memberRepository.findByEmail(toEmail).get().getId();
        //follow 관계 불러옴
        Optional<Follows> relation = followRepository.isFollow(fromMemberId, toMemberId);
        //팔로우 취소
        if (relation.isPresent()) {
            followRepository.delete(relation.get());
            return fromEmail + " 님이 " + toEmail + " 님을 팔로우 취소 했습니다.";
        }
        //팔로우
        followRepository.save(new Follows(fromMemberId, toMemberId));
        return fromEmail + " 님이 " + toEmail + " 님을 팔로우 했습니다.";
    }


}
