package com.kiki.ecommerce.security;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;


@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String jwtSecret;

    @Value("${app.jwt.expiration-ms}")
    private Long expiration;

    private Key getSigningKey() {
        //Key必須使用byte[]
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }


    //生成JWT token
    public String generateToken(UserDetails userDetails) {
        //從UserDetails中提取role
        String fullRole = userDetails.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .orElse("ROLE_USER");
        
        //去掉ROLE_前綴
        String role = fullRole;
        if (fullRole.startsWith("ROLE_")) {
            role = fullRole.substring(5);
        }

        return Jwts.builder()
                    .setSubject(userDetails.getUsername()) //設定主體
                    .claim("role", role)
                    .setIssuedAt(new Date()) //設定發行時間
                    .setExpiration(new Date(System.currentTimeMillis() + expiration)) //設定過期時間
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256) //設定簽名算法和密鑰
                    .compact();
    }

    //從token中提取用戶名
    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build() 
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    //驗證token是否有效
    public boolean isTokenValid(String token, String username) {
        return username.equals(extractUsername(token)) && !isExpired(token);
    }


    //驗證token是否過期
    public boolean isExpired(String token) {
        Date expiration = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }
    
}
