package com.kiki.ecommerce.entity;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "order_items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //一筆明細是屬於哪一個訂單
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    //數量
    @Column(nullable = false)
    private Integer quantity;

    //下單時的商品售價 （價格可能會有異動）
    @Column(nullable = false)
    private BigDecimal unitPrice;
}
