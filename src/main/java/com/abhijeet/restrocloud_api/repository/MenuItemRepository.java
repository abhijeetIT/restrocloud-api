package com.abhijeet.restrocloud_api.repository;

import com.abhijeet.restrocloud_api.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {
}
