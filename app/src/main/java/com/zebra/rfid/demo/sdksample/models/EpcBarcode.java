package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class EpcBarcode implements Serializable {
    private String id;
    private Barcode barcode;

    public EpcBarcode() {
    }

    public EpcBarcode(String id, Barcode barcode) {
        this.id = id;
        this.barcode = barcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }
}