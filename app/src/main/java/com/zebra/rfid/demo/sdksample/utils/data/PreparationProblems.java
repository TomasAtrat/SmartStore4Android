package com.zebra.rfid.demo.sdksample.utils.data;

import static java.lang.System.lineSeparator;

import com.zebra.rfid.demo.sdksample.models.Barcode;

import java.io.Serializable;

public class PreparationProblems implements Serializable {
    private Barcode barcode;
    private Integer supposedQtyPrepared;
    private Integer realQtyPrepared;
    private Integer difference;

    public PreparationProblems(Barcode barcode, Integer supposedQtyPrepared, Integer realQtyPrepared, Integer difference) {
        this.barcode = barcode;
        this.supposedQtyPrepared = supposedQtyPrepared;
        this.realQtyPrepared = realQtyPrepared;
        this.difference = difference;
    }

    public Barcode getBarcode() {
        return barcode;
    }

    public void setBarcode(Barcode barcode) {
        this.barcode = barcode;
    }

    public Integer getSupposedQtyPrepared() {
        return supposedQtyPrepared;
    }

    public void setSupposedQtyPrepared(Integer supposedQtyPrepared) {
        this.supposedQtyPrepared = supposedQtyPrepared;
    }

    public Integer getRealQtyPrepared() {
        return realQtyPrepared;
    }

    public void setRealQtyPrepared(Integer realQtyPrepared) {
        this.realQtyPrepared = realQtyPrepared;
    }

    public Integer getDifference() {
        return difference;
    }

    public void setDifference(Integer difference) {
        this.difference = difference;
    }

    @Override
    public String toString() {
        return "PROBLEMA: " +
                barcode +
                "-> Diferencia = " + difference +
                lineSeparator()
                + "Cantidad leída / Cantidad supuestamente preparada = " + realQtyPrepared
                + " / " + supposedQtyPrepared;
    }
}
