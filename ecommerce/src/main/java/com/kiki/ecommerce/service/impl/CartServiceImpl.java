package com.kiki.ecommerce.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kiki.ecommerce.dto.cart.AddCartItemReqDto;
import com.kiki.ecommerce.dto.cart.CartItemRespDto;
import com.kiki.ecommerce.entity.*;
import com.kiki.ecommerce.repository.CartItemRepository;
import com.kiki.ecommerce.repository.ProductRepository;
import com.kiki.ecommerce.repository.UserRepository;
import com.kiki.ecommerce.service.CartService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;
    
    @Override
    public void addItem(Long userId, AddCartItemReqDto reqDto) {
        if (reqDto.getQuantity() == null || reqDto.getQuantity() <= 0) {
            throw new BizException(400,"數量必須大於0");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new BizException(404, "使用者不存在"));

        Product product = productRepo.findById(reqDto.getProductId())
                .orElseThrow(() -> new BizException(404, "商品不存在"));

        //檢查購物車內是否已經有這個商品
        CartItem cartItem = cartItemRepo.findByUserIdAndProductId(userId, product.getId());

        if (cartItem == null) {
            //如果購物車沒有就新建一筆
            cartItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(reqDto.getQuantity())
                    .build();
        } else {
            //如果已存在就更新數量
            cartItem.setQuantity(cartItem.getQuantity() + reqDto.getQuantity());
        }

        cartItemRepo.save(cartItem);
    }

    @Override
    public List<CartItemRespDto> getCartItems(Long userId) {
        List<CartItem> cartItems = cartItemRepo.findByUserId(userId);
        return cartItems.stream().map(ci -> {
                Product p = ci.getProduct();
                BigDecimal unitPrice = p.getPrice();
                BigDecimal subtotal = unitPrice.multiply(BigDecimal.valueOf(ci.getQuantity()));

                return CartItemRespDto.builder()
                        .id(ci.getId())
                        .productId(p.getId())
                        .productName(p.getName())
                        .quantity(ci.getQuantity())
                        .unitPrice(unitPrice)
                        .subtotal(subtotal)
                        .build();
        })
        .toList();

    }

    @Override
    public void removeItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepo.findById(cartItemId)
                .orElseThrow(() -> new BizException(404, "購物車商品不存在"));

        //確認商品是屬於目前的 User 
        if (!cartItem.getUser().getId().equals(userId)) {
            throw new BizException(403, "無權限刪除此商品");
        }

        cartItemRepo.delete(cartItem);

    }

}
