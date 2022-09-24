package com.zebra.rfid.demo.sdksample.models;

import java.math.BigDecimal;

public class Product {
    private String id;
    private String description;
    private String category;
    private String model;
    private String brand;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("%s %s %s", id, model, brand);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(Integer minQuantity) {
        this.minQuantity = minQuantity;
    }

    public Integer getResupplyQuantity() {
        return resupplyQuantity;
    }

    public void setResupplyQuantity(Integer resupplyQuantity) {
        this.resupplyQuantity = resupplyQuantity;
    }

    public Product(String id, String description, String category, String model, String brand, BigDecimal price, Integer minQuantity, Integer resupplyQuantity) {
        this.id = id;
        this.description = description;
        this.category = category;
        this.model = model;
        this.brand = brand;
        this.price = price;
        this.minQuantity = minQuantity;
        this.resupplyQuantity = resupplyQuantity;
    }

    private Integer minQuantity;
    private Integer resupplyQuantity;
}
