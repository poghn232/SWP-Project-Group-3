package com.example.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "TableOrderDetails")
public class TableOrderDetails {

    @Id
    @Column(name = "table_order_id")
    private UUID tableOrderId;

    @Column(name = "table_number")
    private Integer tableNumber;

    @Column(name = "table_order_date")
    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private TableSlot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = true)
    private Order order;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TableStatus tableStatus = TableStatus.AVAILABLE;
}

