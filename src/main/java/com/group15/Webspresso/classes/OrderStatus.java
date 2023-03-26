package com.group15.Webspresso.classes;

public enum OrderStatus {
    RECIEVED("Order Recieved"),
    SHIPPED("Shipped"),
    DELIVERED("Delivered");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
