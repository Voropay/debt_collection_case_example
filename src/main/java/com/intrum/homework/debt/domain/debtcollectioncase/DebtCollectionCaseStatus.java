package com.intrum.homework.debt.domain.debtcollectioncase;

public enum DebtCollectionCaseStatus {
    INPROCESS(1),
    PAID(2),
    DEFAULTED(3);

    private int status;

    DebtCollectionCaseStatus(int status) {
        this.status = status;
    }

    public int status() {
        return status;
    }
}
