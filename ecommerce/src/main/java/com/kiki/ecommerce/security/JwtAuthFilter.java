package com.kiki.ecommerce.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter { 
    //OncePerRequestFilter = 保證每次 HTTP 請求只會執行一次這個Filter

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
            throws ServletException, IOException {

        //從 Header中提取 Authorization的欄位
        final String authHeader = request.getHeader("Authorization");

        //檢查是否有帶token, 格式是否為Bearer開頭
        //格式 Authorization: Bearer <token>
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            
            //如果沒有token，代表未登入 放行給下一個Filter
            filterChain.doFilter(request, response);
            return;
        }

        //提取token, 去掉 "Bearer "
        final String jwt  = authHeader.substring(7);

        jwtService.isExpired(jwt);

        //從token中解析username 
        final String username = jwtService.extractUsername(jwt);

        //如果成功解析 username, 而且沒有Authentication (避免重複驗證)
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //建立一個Authentication物件，代表已經通過驗證
            UsernamePasswordAuthenticationToken authToken = 
                new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null, //密碼不需要
                    userDetails.getAuthorities() //權限
                );

            //把request資訊加入Authentication裡
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            //把Authentication放到SecurityContext中，代表用戶已經通過驗證
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        //放行給下一個Filter
        filterChain.doFilter(request, response);
    }
    
}
