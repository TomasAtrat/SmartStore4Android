package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;
import java.util.Date;

public class ReceptionList implements Serializable {
    private Long id;
    private String description;
    private Date startingDate;
    private Date endingDate;

    public ReceptionList() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartingDate() {
        return startingDate;
    }

    public void setStartingDate(Date startingDate) {
        this.startingDate = startingDate;
    }

    public Date getEndingDate() {
        return endingDate;
    }

    public void setEndingDate(Date endingDate) {
        this.endingDate = endingDate;
    }
}