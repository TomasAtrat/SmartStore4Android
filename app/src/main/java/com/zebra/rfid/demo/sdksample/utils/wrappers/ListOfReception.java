package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.ReceptionList;

import java.util.List;

public class ListOfReception {
    private List<ReceptionList> references;

    public List<ReceptionList> getReferences() {
        return references;
    }

    public void setReferences(List<ReceptionList> inventories) {
        this.references = inventories;
    }

    public ListOfReception(List<ReceptionList> inventories) {
        this.references = inventories;
    }
}
