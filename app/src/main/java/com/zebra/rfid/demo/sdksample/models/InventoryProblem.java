package com.zebra.rfid.demo.sdksample.models;

public class InventoryProblem {
    private Long id;
    private String productCode;
    private Integer difference;
    private String description;
    private Boolean accepted;
    private InventoryDetail detail;

    public InventoryProblem(String productCode, Integer difference, String description, InventoryDetail detail) {
        this.productCode = productCode;
        this.difference = difference;
        this.description = description;
        this.detail = detail;
    }

    public InventoryProblem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public InventoryDetail getDetail() {
        return detail;
    }

    public void setDetail(InventoryDetail detail) {
        this.detail = detail;
    }
}