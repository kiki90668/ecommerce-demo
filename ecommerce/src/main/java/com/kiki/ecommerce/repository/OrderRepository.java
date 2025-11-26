package com.kiki.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kiki.ecommerce.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
