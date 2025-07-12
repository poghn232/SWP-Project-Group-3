package com.example.demo.api.dto.party;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private UUID orderId;
    private String partyName;
    private LocalDateTime orderDate;
    private Double totalPrice;
    private String paymentStatus;
}
