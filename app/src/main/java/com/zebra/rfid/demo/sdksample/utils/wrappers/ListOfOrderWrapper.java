package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.OrderInfo;

import java.io.Serializable;
import java.util.List;

public class ListOfOrderWrapper implements Serializable {
    List<OrderInfo> orders;

    public ListOfOrderWrapper(List<OrderInfo> orders) {
        this.orders = orders;
    }

    public List<OrderInfo> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderInfo> orders) {
        this.orders = orders;
    }
}