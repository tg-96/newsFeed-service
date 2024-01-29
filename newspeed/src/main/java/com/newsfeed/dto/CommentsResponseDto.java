package com.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommentsResponseDto {
    private String writerEmail;
    private String name;
    private String text;
}
