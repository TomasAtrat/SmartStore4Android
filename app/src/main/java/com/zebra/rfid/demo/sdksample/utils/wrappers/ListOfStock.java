package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.Stock;

import java.io.Serializable;
import java.util.List;

public class ListOfStock implements Serializable {
    private List<Stock> stockList;

    public ListOfStock(List<Stock> stockList) {
        this.stockList = stockList;
    }

    public List<Stock> getStockList() {
        return stockList;
    }

    public void setStockList(List<Stock> stockList) {
        this.stockList = stockList;
    }
}