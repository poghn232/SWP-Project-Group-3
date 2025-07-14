package com.example.demo.repository;

import com.example.demo.model.Order;
import com.example.demo.model.TableOrderDetails;
import com.example.demo.model.TableSlot;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface TableOrderDetailsRepository extends JpaRepository<TableOrderDetails, UUID> {

    List<TableOrderDetails> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    List<TableOrderDetails> findAllByOrderDate(LocalDate date);

    List<TableOrderDetails> findAllBySlot(TableSlot slot);

    @Modifying
    @Transactional
    @Query("DELETE FROM TableOrderDetails tod WHERE tod.orderDate < :cutoffDate")
    int deleteOrdersBefore(@Param("cutoffDate") LocalDate cutoffDate);
}
