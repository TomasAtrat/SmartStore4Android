package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;
import java.util.Date;

public class OrderInfo implements Serializable {
    private Long id;
    private int version;
    private Date deliveryDate;
    private Date addrowDate;
    private String address;
    private Boolean acceptsPartialExpedition;
    private String description1;
    private String description2;
    private String description3;
    private String description4;
    private ExpeditionType expedition;
    private Branch branch;
    private Customer customer;

    public OrderInfo(Date addrowDate, String address, Boolean acceptsPartialExpedition, String description1, String description2, String description3, String description4, ExpeditionType expedition, Branch branch, Customer customer) {
        this.addrowDate = addrowDate;
        this.address = address;
        this.acceptsPartialExpedition = acceptsPartialExpedition;
        this.description1 = description1;
        this.description2 = description2;
        this.description3 = description3;
        this.description4 = description4;
        this.expedition = expedition;
        this.branch = branch;
        this.customer = customer;
    }

    public OrderInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getAddrowDate() {
        return addrowDate;
    }

    public void setAddrowDate(Date addrowDate) {
        this.addrowDate = addrowDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getAcceptsPartialExpedition() {
        return acceptsPartialExpedition;
    }

    public void setAcceptsPartialExpedition(Boolean acceptsPartialExpedition) {
        this.acceptsPartialExpedition = acceptsPartialExpedition;
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

    public ExpeditionType getExpedition() {
        return expedition;
    }

    public void setExpedition(ExpeditionType expedition) {
        this.expedition = expedition;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}