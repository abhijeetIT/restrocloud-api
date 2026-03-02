package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order,Long> {
}
