package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class OrderDetail implements Serializable {
    private Long id;
    private Integer orderedQuantity;
    private Integer preparedQuantity;
    private Barcode barcode;
    private OrderInfo orderInfo;

    public OrderDetail(Integer orderedQuantity, Integer preparedQuantity, Barcode barcode, OrderInfo orderInfo) {
        this.orderedQuantity = orderedQuantity;
        this.preparedQuantity = preparedQuantity;
        this.barcode = barcode;
        this.orderInfo = orderInfo;
    }

    public OrderDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(Integer orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }

    public Integer getPreparedQuantity() {
        return preparedQuantity;
    }

    public void setPreparedQuantity(Integer preparedQuantity) {
        this.preparedQuantity = preparedQuantity;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public OrderInfo getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(OrderInfo orderInfo) {
        this.orderInfo = orderInfo;
    }
}