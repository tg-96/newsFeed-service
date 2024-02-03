package com.preOrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentsResponseDto {
    private Long commentsId;
    private Long writeMemberId;
    private String text;
}
