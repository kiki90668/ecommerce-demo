package com.kiki.ecommerce.dto.cart;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartItemRespDto {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice; // 下單時的商品價格
    private BigDecimal subtotal; // 總價 = unitPrice * quantitiy
}
