package com.zebra.rfid.demo.sdksample.models;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Objects;

public class InventoryDetail implements Serializable {
    private Long id;
    private Integer supposedQty;
    private Integer readQty;
    private Integer acceptedQty;
    private Inventory inventory;
    private Barcode barcode;

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return String.format("%d- %s -> %d / %d", id, barcode.toString(), readQty, supposedQty);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSupposedQty() {
        return supposedQty;
    }

    public void setSupposedQty(Integer supposedQty) {
        this.supposedQty = supposedQty;
    }

    public Integer getReadQty() {
        return readQty;
    }

    public void setReadQty(Integer readQty) {
        this.readQty = readQty;
    }

    public Integer getAcceptedQty() {
        return acceptedQty;
    }

    public void setAcceptedQty(Integer acceptedQty) {
        this.acceptedQty = acceptedQty;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public InventoryDetail() {
    }
}

