package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;
import java.util.Date;

public class PreparationDetail implements Serializable {
    private Long id;
    private Integer orderedQty;
    private Integer preparedQty;
    private Date addDate;
    private Date updateDate;
    private Barcode barcode;
    private Preparation preparation;

    public PreparationDetail() {
    }

    public PreparationDetail(Long id, Integer orderedQty, Integer preparedQty, Date addDate, Date updateDate, Barcode barcode, Preparation preparation) {
        this.id = id;
        this.orderedQty = orderedQty;
        this.preparedQty = preparedQty;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.barcode = barcode;
        this.preparation = preparation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOrderedQty() {
        return orderedQty;
    }

    public void setOrderedQty(Integer orderedQty) {
        this.orderedQty = orderedQty;
    }

    public Integer getPreparedQty() {
        return preparedQty;
    }

    public void setPreparedQty(Integer preparedQty) {
        this.preparedQty = preparedQty;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }
}