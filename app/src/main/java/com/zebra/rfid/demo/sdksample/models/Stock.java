package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;
import java.util.Date;

public class Stock implements Serializable {
    private Long id;
    private Integer version;
    private Date addDate;
    private Long qtReserve;
    private Long qtStock;
    private Date updateDate;
    private Barcode barcodeBarcode;
    private Branch branch;

    public Stock(Date addDate, Long qtReserve, Long qtStock, Date updateDate, Barcode barcodeBarcode, Branch branch) {
        this.addDate = addDate;
        this.qtReserve = qtReserve;
        this.qtStock = qtStock;
        this.updateDate = updateDate;
        this.barcodeBarcode = barcodeBarcode;
        this.branch = branch;
    }

    public Stock() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Long getQtReserve() {
        return qtReserve;
    }

    public void setQtReserve(Long qtReserve) {
        this.qtReserve = qtReserve;
    }

    public Long getQtStock() {
        return qtStock;
    }

    public void setQtStock(Long qtStock) {
        this.qtStock = qtStock;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Barcode getBarcodeBarcode() {
        return barcodeBarcode;
    }

    public void setBarcodeBarcode(Barcode barcodeBarcode) {
        this.barcodeBarcode = barcodeBarcode;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}