package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class ReceptionDetail implements Serializable {
    private Long id;
    private Integer scheduledQty;
    private Integer receivedQty;
    private Integer acceptedQty;
    private ReceptionList receptionList;
    private Barcode barcode;

    public ReceptionDetail() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getScheduledQty() {
        return scheduledQty;
    }

    public void setScheduledQty(Integer scheduledQty) {
        this.scheduledQty = scheduledQty;
    }

    public Integer getReceivedQty() {
        return receivedQty;
    }

    public void setReceivedQty(Integer receivedQty) {
        this.receivedQty = receivedQty;
    }

    public Integer getAcceptedQty() {
        return acceptedQty;
    }

    public void setAcceptedQty(Integer acceptedQty) {
        this.acceptedQty = acceptedQty;
    }

    public ReceptionList getReceptionList() {
        return receptionList;
    }

    public void setReceptionList(ReceptionList receptionList) {
        this.receptionList = receptionList;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    @Override
    public String toString() {
        return String.format("%d- %s -> Agendado: %d ", id, barcode.toString(), scheduledQty);
    }
}