package com.newsfeed.service;

import com.newsfeed.repository.ActivitiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ActivityService {
    private final ActivitiesRepository activitiesRepository;

    /**
     *
     */

}
