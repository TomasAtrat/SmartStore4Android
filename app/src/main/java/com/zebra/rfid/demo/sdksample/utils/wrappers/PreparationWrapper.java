package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.Preparation;
import com.zebra.rfid.demo.sdksample.models.PreparationDetail;

import java.io.Serializable;
import java.util.List;

public class PreparationWrapper implements Serializable {
    private Preparation preparation;
    private List<PreparationDetail> details;

    public PreparationWrapper(Preparation preparation, List<PreparationDetail> details) {
        this.preparation = preparation;
        this.details = details;
    }

    public Preparation getPreparation() {
        return preparation;
    }

    public void setPreparation(Preparation preparation) {
        this.preparation = preparation;
    }

    public List<PreparationDetail> getDetails() {
        return details;
    }

    public void setDetails(List<PreparationDetail> details) {
        this.details = details;
    }
}
