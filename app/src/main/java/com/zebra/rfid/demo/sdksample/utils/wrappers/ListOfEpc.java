package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.EpcBarcode;

import java.util.List;

public class ListOfEpc {
    private List<EpcBarcode> epcBarcodeList;

    public ListOfEpc() {
    }

    public ListOfEpc(List<EpcBarcode> epcBarcodeList) {
        this.epcBarcodeList = epcBarcodeList;
    }

    public List<EpcBarcode> getEpcBarcodeList() {
        return epcBarcodeList;
    }

    public void setEpcBarcodeList(List<EpcBarcode> epcBarcodeList) {
        this.epcBarcodeList = epcBarcodeList;
    }
}