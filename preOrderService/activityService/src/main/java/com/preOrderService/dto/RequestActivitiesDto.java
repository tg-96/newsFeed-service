package com.preOrderService.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestActivitiesDto {
    private Long memberId;
    private String fromUserName;
    private String toUserName;
    private String type;
}
