package com.intrum.homework.debt.domain.debtcollectioncase;

import java.util.Date;

public class DebtCollectionCaseStatusRequest {
    private Long id;
    private int status;
    private Date paidDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }
}
