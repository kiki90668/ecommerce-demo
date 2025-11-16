package com.kiki.ecommerce.service;


import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kiki.ecommerce.dto.UserReqDto;
import com.kiki.ecommerce.dto.UserRespDto;
import com.kiki.ecommerce.entity.BizException;
import com.kiki.ecommerce.entity.User;
import com.kiki.ecommerce.repository.UserRepository;
import com.kiki.ecommerce.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserRespDto register(UserReqDto dto) {
        if (userRepository.findByUsername(dto.getUsername()).isPresent()) {
            throw new BizException(400, "User already exists.");
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
                .orElseThrow(() -> new BizException(400, "User not found"));

        //驗證密碼
        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException(400, "Password is incorrect");
        }

        //生成JWT token
        String token = jwtService.generateToken(user.getUsername());

        return UserRespDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .token(token)
                .build();
    }
}
