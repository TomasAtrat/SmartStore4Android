package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;
import java.util.Date;

public class Preparation implements Serializable {
    private Long id;
    private Date startingDate;
    private Date endingDate;
    private Boolean isFinished;
    private Boolean hasProblems;
    private Date addDate;
    private Date updateDate;
    private UserInfo userAssigned;
    private OrderInfo order;

    public Preparation(Long id, Date startingDate, Date endingDate, Boolean isFinished, Boolean hasProblems, Date addDate, Date updateDate, UserInfo userAssigned, OrderInfo order) {
        this.id = id;
        this.startingDate = startingDate;
        this.endingDate = endingDate;
        this.isFinished = isFinished;
        this.hasProblems = hasProblems;
        this.addDate = addDate;
        this.updateDate = updateDate;
        this.userAssigned = userAssigned;
        this.order = order;
    }

    public Preparation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public Boolean getHasProblems() {
        return hasProblems;
    }

    public void setHasProblems(Boolean hasProblems) {
        this.hasProblems = hasProblems;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public UserInfo getUserAssigned() {
        return userAssigned;
    }

    public void setUserAssigned(UserInfo userAssigned) {
        this.userAssigned = userAssigned;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
