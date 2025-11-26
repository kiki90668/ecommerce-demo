package com.kiki.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki.ecommerce.entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

    //查某張訂單的所有明細
    List<OrderItem> findByOrderId(Long orderId);

}
