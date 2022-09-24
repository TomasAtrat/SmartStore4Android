package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.Inventory;

import java.util.List;

public class ListOfInventories {
    private List<Inventory> inventories;

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public ListOfInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }
}
