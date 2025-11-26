package com.kiki.ecommerce.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kiki.ecommerce.common.ApiResponse;
import com.kiki.ecommerce.dto.cart.AddCartItemReqDto;
import com.kiki.ecommerce.dto.cart.CartItemRespDto;
import com.kiki.ecommerce.entity.BizException;
import com.kiki.ecommerce.entity.User;
import com.kiki.ecommerce.repository.UserRepository;
import com.kiki.ecommerce.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
    
    private final CartService cartService;
    private final UserRepository userRepo;

    // 從目前登入的JWT拿 userID
    private Long getCurrentUserId(Authentication authentacation) {
        if (authentacation == null || !(authentacation.getPrincipal() instanceof UserDetails userDetails)) {
            throw new BizException(403, "未登入");
        }

        String username = userDetails.getUsername();
        
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new BizException(404, "使用者不存在"));
        
        return user.getId();
    }

    //加入購物車
    @PostMapping("/add")
    public ApiResponse<Void> addToCart(@RequestBody AddCartItemReqDto reqDto, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);
        
        cartService.addItem(userId, reqDto);
        return ApiResponse.success("成功加入購物車", null);
    }

    //查看購物車
    @GetMapping
    public ApiResponse<List<CartItemRespDto>> listCart(Authentication authentacation) {
       Long userId =  getCurrentUserId(authentacation);

        List<CartItemRespDto> items = cartService.getCartItems(userId);

        return ApiResponse.success("查詢成功", items);
    }

    //刪除購物車商品
    @DeleteMapping("/{cartItemId}")
    public ApiResponse<Void> removeFromCart(@PathVariable Long cartItemId, Authentication authentication) {
        Long userId = getCurrentUserId(authentication);

        cartService.removeItem(userId, cartItemId);

        return ApiResponse.success("刪除成功", null);
    }

}
