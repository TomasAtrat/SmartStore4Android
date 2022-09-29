package com.zebra.rfid.demo.sdksample.utils.wrappers;

import com.zebra.rfid.demo.sdksample.models.ReceptionDetail;
import com.zebra.rfid.demo.sdksample.models.ReceptionList;
import com.zebra.rfid.demo.sdksample.models.ReceptionProblem;

import java.util.List;

public class Reception {
    private ReceptionList receptionList;
    private List<ReceptionDetail> details;
    private List<ReceptionProblem> problems;

    public Reception() {
    }

    public Reception(ReceptionList receptionList, List<ReceptionDetail> details, List<ReceptionProblem> problems) {
        this.receptionList = receptionList;
        this.details = details;
        this.problems = problems;
    }

    public ReceptionList getReceptionList() {
        return receptionList;
    }

    public void setReceptionList(ReceptionList receptionList) {
        this.receptionList = receptionList;
    }

    public List<ReceptionDetail> getDetails() {
        return details;
    }

    public void setDetails(List<ReceptionDetail> details) {
        this.details = details;
    }

    public List<ReceptionProblem> getProblems() {
        return problems;
    }

    public void setProblems(List<ReceptionProblem> problems) {
        this.problems = problems;
    }
}