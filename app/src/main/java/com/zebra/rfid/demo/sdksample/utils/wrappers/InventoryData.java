package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.models.InventoryDetail;

import java.util.List;


public class InventoryData {
    private Inventory inventory;
    private List<InventoryDetail> details;

    public List<InventoryDetail> getDetails() {
        return details;
    }

    public void setDetails(List<InventoryDetail> details) {
        this.details = details;
    }


    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public InventoryData(Inventory inventory) {
        this.inventory = inventory;
    }
}
