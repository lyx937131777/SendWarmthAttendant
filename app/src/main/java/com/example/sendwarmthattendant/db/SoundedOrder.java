package com.example.sendwarmthattendant.db;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class SoundedOrder extends LitePalSupport {
    private String orderId;

    public SoundedOrder(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
