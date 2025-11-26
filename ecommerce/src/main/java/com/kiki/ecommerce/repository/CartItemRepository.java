package com.kiki.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki.ecommerce.entity.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    //查User的所有購物車內容
    List<CartItem> findByUserId(Long userId);

    //查User + 商品(檢查是不是在購物車內)
    CartItem findByUserIdAndProductId(Long userId, Long productId);
}
