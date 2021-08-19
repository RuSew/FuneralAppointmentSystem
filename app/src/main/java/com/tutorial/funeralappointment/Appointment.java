package com.tutorial.funeralappointment;

public class Appointment {
    private String refNo;
    private String time;
    private boolean isCancelled;
    private boolean isDone;
    private String custId;
    private String createdDated;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getCreatedDated() {
        return createdDated;
    }

    public void setCreatedDated(String createdDated) {
        this.createdDated = createdDated;
    }
}
