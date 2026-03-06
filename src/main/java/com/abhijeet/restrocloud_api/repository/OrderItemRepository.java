package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
