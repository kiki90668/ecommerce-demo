package com.kiki.ecommerce.service;

import java.util.List;

import com.kiki.ecommerce.dto.cart.AddCartItemReqDto;
import com.kiki.ecommerce.dto.cart.CartItemRespDto;

public interface CartService {
    // 加入購物車
    void addItem(Long userId, AddCartItemReqDto reqDto);

    // 查詢購物車列表
    List<CartItemRespDto> getCartItems(Long userId);

    // 刪除購物車的商品
    void removeItem(Long userId, Long cartItemId);
}
