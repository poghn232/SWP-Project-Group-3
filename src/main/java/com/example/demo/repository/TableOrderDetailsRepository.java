package com.example.demo.repository;

import com.example.demo.model.TableOrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface TableOrderDetailsRepository extends JpaRepository<TableOrderDetails, UUID> {

    List<TableOrderDetails> findAllByOrderDateBetween(LocalDate startDate, LocalDate endDate);

    List<TableOrderDetails> findAllByOrderDate(LocalDate date);
}
