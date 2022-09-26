package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.Inventory;
import com.zebra.rfid.demo.sdksample.models.InventoryDetail;
import com.zebra.rfid.demo.sdksample.models.InventoryProblem;

import java.util.List;


public class InventoryData {
    private Inventory inventory;
    private List<InventoryDetail> details;
    private List<InventoryProblem> problems;

    public List<InventoryProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<InventoryProblem> problems) {
        this.problems = problems;
    }

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

    public InventoryData(Inventory inventory, List<InventoryDetail> details, List<InventoryProblem> problems) {
        this.inventory = inventory;
        this.details = details;
        this.problems = problems;
    }
}
