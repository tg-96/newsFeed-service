package com.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentLikesDto {
    private Long commentId;
    private String likersEmail;
}
