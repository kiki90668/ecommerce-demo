package com.kiki.ecommerce.dto.userDto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserReqDto {
    private String username;
    private String password;
    private String mobileNo;

}
