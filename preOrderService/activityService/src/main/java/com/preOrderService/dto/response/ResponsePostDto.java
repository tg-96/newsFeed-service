package com.preOrderService.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ResponsePostDto {
    private String text;
    private Long writerId;
    private String image;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @Builder
    public ResponsePostDto(String text, Long writerId, String image, LocalDateTime createDate, LocalDateTime updateDate) {
        this.text = text;
        this.writerId = writerId;
        this.image = image;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
