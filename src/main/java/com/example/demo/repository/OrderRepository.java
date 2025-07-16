package com.example.demo.repository;

import com.example.demo.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByCustomerName(String customerName);

    List<Order> findAllByStatusAndExpirationDateBefore(String status, LocalDateTime date);

    List<Order> findAllByExpirationDateBefore(LocalDateTime date);

    List<Order> findByCustomerNameAndStatus(String customerName, String status);
}
