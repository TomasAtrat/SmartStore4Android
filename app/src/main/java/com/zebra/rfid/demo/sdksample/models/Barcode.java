package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class Barcode implements Serializable {
    private String id;
    private String colour;
    private String size;
    private String description1;
    private String description2;
    private String description3;
    private String description4;
    private Product product;

    public Barcode(String id) {
        this.id = id;
    }

    public Barcode(String id, String colour, String size, String description1, String description2, String description3, String description4, Product product) {
        this.id = id;
        this.colour = colour;
        this.size = size;
        this.description1 = description1;
        this.description2 = description2;
        this.description3 = description3;
        this.description4 = description4;
        this.product = product;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription1() {
        return description1;
    }

    public void setDescription1(String description1) {
        this.description1 = description1;
    }

    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }

    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

    public String getDescription4() {
        return description4;
    }

    public void setDescription4(String description4) {
        this.description4 = description4;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", product.toString(), id);
    }
}
