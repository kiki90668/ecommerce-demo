package com.kiki.ecommerce.service;

import com.kiki.ecommerce.dto.userDto.UserReqDto;
import com.kiki.ecommerce.dto.userDto.UserRespDto;


public interface UserService {
    public UserRespDto register(UserReqDto dto);
    public UserRespDto login(UserReqDto dto);
}
