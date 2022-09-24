package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;
import java.util.Date;

public class Inventory implements Serializable {
    private Long id;
    private String description;
    private Date startingDate;
    private Date endingDate;
    private Date addDate;
    private Boolean active;
    private UserInfo userAssigned;

    public Inventory(Long id, String description, Date startingDate, Date endingDate, Date addDate, Boolean active, UserInfo userAssigned) {
        this.id = id;
        this.description = description;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.addDate = addDate;
        this.active = active;
        this.userAssigned = userAssigned;
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

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UserInfo getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(UserInfo userAssigned) {
        this.userAssigned = userAssigned;
    }


    @Override
    public String toString() {
        return String.format("%d - %s", id, description);
    }
}
