package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.ExpeditionType;

import java.util.List;

public class ListOfExpeditionType {
    private List<ExpeditionType> expeditionTypeList;

    public ListOfExpeditionType(List<ExpeditionType> expeditionTypeList) {
        this.expeditionTypeList = expeditionTypeList;
    }

    public ListOfExpeditionType() {
    }

    public List<ExpeditionType> getExpeditionTypeList() {
        return expeditionTypeList;
    }

    public void setExpeditionTypeList(List<ExpeditionType> expeditionTypeList) {
        this.expeditionTypeList = expeditionTypeList;
    }
}