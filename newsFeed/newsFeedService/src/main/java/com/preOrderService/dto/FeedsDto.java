package com.preOrderService.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
public class FeedsDto {
    private String text;
    private Long writerId;
    private String image;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder
    public FeedsDto(String text, Long writerId, String image, LocalDateTime createDate, LocalDateTime updateDate) {
        this.text = text;
        this.writerId = writerId;
        this.image = image;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
