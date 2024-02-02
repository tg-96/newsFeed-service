package com.preOrderService.service;

import com.preOrderService.dto.ActivitiesDto;
import com.preOrderService.entity.Activities;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ActivityService {
    /**
     * 나의 활동 조회
     */
    public List<ActivitiesDto> findActivities(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if(member.isEmpty()){
            throw new RuntimeException("활동을 조회하려는 회원이 존재하지 않습니다.");
        }

        List<Activities> activities = activitiesRepository.findByOwnerId(member.get().getId());
        return activities.stream().map(activity -> new ActivitiesDto(activity.getNotification()))
                .collect(Collectors.toList());
    }

}
