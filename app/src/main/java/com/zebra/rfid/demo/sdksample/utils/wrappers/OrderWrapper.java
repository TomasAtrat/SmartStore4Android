package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.OrderDetail;
import com.zebra.rfid.demo.sdksample.models.OrderInfo;

import java.io.Serializable;
import java.util.List;

public class OrderWrapper implements Serializable {
    private OrderInfo orderInfo;
    private List<OrderDetail> orderDetailList;

    public OrderWrapper() {
    }

    public OrderWrapper(OrderInfo orderInfo, List<OrderDetail> orderDetailList) {
        this.orderInfo = orderInfo;
        this.orderDetailList = orderDetailList;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }

    public List<OrderDetail> getOrderDetailList() {
        return orderDetailList;
    }

    public void setOrderDetailList(List<OrderDetail> orderDetailList) {
        this.orderDetailList = orderDetailList;
    }
}