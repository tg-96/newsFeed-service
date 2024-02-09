package com.preOrderService.api.internal;
import com.preOrderService.dto.response.ResponsePostDto;
import com.preOrderService.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InternalActivityController {
    private final ActivityService activityService;

    /**
     * post 조회
     */
    @GetMapping("/activities/post")
    public ResponsePostDto getPost(@RequestParam("postId") Long postId) {
        System.out.println("postId = " + postId);
        return activityService.getPost(postId);
    }


}
