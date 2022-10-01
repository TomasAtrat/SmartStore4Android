package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class ReceptionProblem implements Serializable {
    private Long id;
    private String productCode;
    private Integer difference;
    private String description;
    private Boolean accepted;
    private ReceptionDetail detail;

    public ReceptionProblem() {
    }

    public Long getId() {
        return id;
    }

    public ReceptionProblem(String productCode, Integer difference, String description, Boolean accepted, ReceptionDetail detail) {
        this.productCode = productCode;
        this.difference = difference;
        this.description = description;
        this.accepted = accepted;
        this.detail = detail;
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

    public ReceptionDetail getDetail() {
        return detail;
    }

    public void setDetail(ReceptionDetail detail) {
        this.detail = detail;
    }
}