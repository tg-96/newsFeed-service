package com.preOrderService.dto;

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
    private Long memberId;
    private String image;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
