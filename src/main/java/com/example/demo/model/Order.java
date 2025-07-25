package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Orders")
public class Order {
    @Id
    @Column(name = "order_id")
    private UUID orderId;

    @ManyToOne
    @JoinColumn(name = "party_id")
    private Party partyOrder;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<TableOrderDetails> tableOrderDetails;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    private LocalDateTime expirationDate;

    @Column(name = "total_price", nullable = false)
    private Double totalPrice;

    @Column(name = "status", length = 50, nullable = false)
    private String status; // e.g., DRAFT, PENDING, CONFIRMED, COMPLETED, CANCELLED

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "customer_name", length = 100, nullable = false)
    private String customerName;

    @Column(name = "customer_phone", length = 20, nullable = false)
    private String customerPhone;

    @Column(name = "payment_method", length = 50)
    private String paymentMethod;

    @Column(name = "payment_status", length = 50, nullable = false)
    private String paymentStatus; // e.g., UNPAID, PAID, REFUNDED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @PrePersist
    protected void onCreate() {

        if (this.orderId == null) {
            this.orderId = UUID.randomUUID();
        }
        if (orderDate == null) {
            orderDate = LocalDateTime.now();
        }
        if (status == null) {
            status = "DRAFT";
        }
        if (totalPrice == null) {
            totalPrice = 0.0;
        }
        if (paymentStatus == null) {
            paymentStatus = "UNPAID";
        }
    }
}