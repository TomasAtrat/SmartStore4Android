package com.zebra.rfid.demo.sdksample.models;

import java.io.Serializable;

public class Branch implements Serializable {
    private Long id;
    private String description;

    public Branch(Long id) {
        this.id = id;
    }

    public Branch() {
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

    @Override
    public String toString() {
        return String.format("%d - %s", id, description);
    }
}