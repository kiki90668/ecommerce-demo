package com.kiki.ecommerce.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.kiki.ecommerce.service.UserServiceImpl;
import com.kiki.ecommerce.common.ApiResponse;
import com.kiki.ecommerce.dto.UserReqDto;
import com.kiki.ecommerce.dto.UserRespDto;


@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    //註冊
    @PostMapping("/register")
    public ApiResponse<UserRespDto> register(@RequestBody UserReqDto repDto) {
        return ApiResponse.success("User registered successfully", userServiceImpl.register(repDto));
    }

    //登入
    @PostMapping("/login")
    public ApiResponse<UserRespDto> login(@RequestBody UserReqDto reqDto) {
        return ApiResponse.success("User logged in successfully", userServiceImpl.login(reqDto));
    }

}
