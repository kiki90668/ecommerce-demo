package com.kiki.ecommerce.service;

import com.kiki.ecommerce.dto.UserReqDto;
import com.kiki.ecommerce.dto.UserRespDto;


public interface UserService {
    public UserRespDto register(UserReqDto dto);
    public UserRespDto login(UserReqDto dto);
}
