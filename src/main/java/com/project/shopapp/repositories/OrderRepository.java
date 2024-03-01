package com.project.shopapp.repositories;

import com.project.shopapp.models.Order;
import com.project.shopapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(User user);
}
