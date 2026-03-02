package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {
}
