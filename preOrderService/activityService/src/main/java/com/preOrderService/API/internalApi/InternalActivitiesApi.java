package com.preOrderService.API.internalApi;

import com.preOrderService.dto.RequestActivitiesDto;
import com.preOrderService.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InternalActivitiesApi {
    @Autowired
    ActivityService activityService;
    @PostMapping("/activities")
    public ResponseEntity<Void> addActivities(@RequestBody RequestActivitiesDto request){
        activityService.addActivities(request);
        
        return ResponseEntity.ok().build();
    }

}
