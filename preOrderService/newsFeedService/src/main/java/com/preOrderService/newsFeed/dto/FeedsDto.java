package com.preOrderService.newsFeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedsDto {
    private String text;
    private String writerEmail;
    private String name;
    private String image;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
