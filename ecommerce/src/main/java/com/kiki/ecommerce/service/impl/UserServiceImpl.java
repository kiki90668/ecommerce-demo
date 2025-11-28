package com.kiki.ecommerce.service.impl;


import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kiki.ecommerce.dto.userDto.UserReqDto;
import com.kiki.ecommerce.dto.userDto.UserRespDto;
import com.kiki.ecommerce.entity.BizException;
import com.kiki.ecommerce.entity.User;
import com.kiki.ecommerce.repository.UserRepository;
import com.kiki.ecommerce.security.JwtService;
import com.kiki.ecommerce.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserRespDto register(UserReqDto dto) {
        if (!userRepository.existsByUsername(dto.getUsername())) {
            throw new BizException(400, "使用者已存在");
        }
        User user = User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role("USER")
                .build();

        //儲存user
        userRepository.save(user);

        return UserRespDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .build();
    }

    @Override
    public UserRespDto login(UserReqDto dto) {
        //確認使用者是否存在
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new BizException(404, "使用者不存在"));

        //驗證密碼
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException(404, "密碼錯誤");
        }

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
            user.getUsername(),
            user.getPassword(),
            List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );

        //生成JWT token
        String token = jwtService.generateToken(userDetails);

        return UserRespDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
    }
}
