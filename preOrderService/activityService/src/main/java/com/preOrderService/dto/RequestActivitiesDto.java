package com.preOrderService.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestActivitiesDto {
    private Long memberId;
    private String fromUserName;
    private String toUserName;
    private String type;
}
