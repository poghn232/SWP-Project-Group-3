package com.example.demo.model;

import java.math.BigDecimal;
import java.util.Date;

public class Order {
    private int orderId;
    private int customerId;
    private Date orderTime;
    private String deliveryAddress;
    private String status;
    private String paymentStatus;
    private int shipperId;
    private int tableId;
    private BigDecimal totalAmount;

    public Order(int orderId, int customerId, Date orderTime, String deliveryAddress, String status, String paymentStatus,
            int shipperId, int tableId, BigDecimal totalAmount) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.orderTime = orderTime;
        this.deliveryAddress = deliveryAddress;
        this.status = status;
        this.paymentStatus = paymentStatus;
        this.shipperId = shipperId;
        this.tableId = tableId;
        this.totalAmount = totalAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public int getShipperId() {
        return shipperId;
    }

    public void setShipperId(int shipperId) {
        this.shipperId = shipperId;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}