package com.preOrderService.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentsResponseDto {
    private Long commentsId;
    private Long writerId;
    private String text;
}
