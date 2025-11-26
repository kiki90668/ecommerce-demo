package com.kiki.ecommerce.controller;



import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.kiki.ecommerce.common.ApiResponse;
import com.kiki.ecommerce.dto.userDto.UserReqDto;
import com.kiki.ecommerce.dto.userDto.UserRespDto;
import com.kiki.ecommerce.service.UserService;



@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //註冊
    @PostMapping("/register")
    public ApiResponse<UserRespDto> register(@RequestBody UserReqDto repDto) {
        return ApiResponse.success("User registered successfully", userService.register(repDto));
    }

    //登入
    @PostMapping("/login")
    public ApiResponse<UserRespDto> login(@RequestBody UserReqDto reqDto) {
        return ApiResponse.success("User logged in successfully", userService.login(reqDto));
    }

}
