package com.example.demo.model;

import java.util.Date;

public class VoucherUsage {
    private int voucherUsageId;
    private int voucherId;
    private int customerId;
    private int orderId;
    private Date usedAt;

    public VoucherUsage(int voucherUsageId, int voucherId, int customerId, int orderId, Date usedAt) {
        this.voucherUsageId = voucherUsageId;
        this.voucherId = voucherId;
        this.customerId = customerId;
        this.orderId = orderId;
        this.usedAt = usedAt;
    }

    public int getVoucherUsageId() {
        return voucherUsageId;
    }

    public void setVoucherUsageId(int voucherUsageId) {
        this.voucherUsageId = voucherUsageId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public Date getUsedAt() {
        return usedAt;
    }

    public void setUsedAt(Date usedAt) {
        this.usedAt = usedAt;
    }
}
