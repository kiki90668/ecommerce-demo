package com.kiki.ecommerce.dto.userDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRespDto {
    private Long id;
    private String username;
    private String token;
}
