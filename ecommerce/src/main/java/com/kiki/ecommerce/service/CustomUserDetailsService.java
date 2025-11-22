package com.kiki.ecommerce.service;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.kiki.ecommerce.entity.User;
import com.kiki.ecommerce.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
    
    private final UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //從資料庫查 username
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("user" + username + "not found"));

        //回傳給Spring Security的 UserDetails
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole())
                .build();
    }

}
