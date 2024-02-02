package com.preOrderService.adaptor;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberAdaptor {
    private Long id;
    private String name;
    private String email;
    private String image;
    private String introduction;
}
