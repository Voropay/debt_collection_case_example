package com.intrum.homework.debt.domain.debtcollectioncase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.customer.CustomerResponse;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.Date;

public class DebtCollectionCaseResponse {
    private Long id;
    private CustomerResponse customer;
    private int status;
    private Date dueDate;
    private Date paidDate;
    private BigDecimal debtAmount;
    private String debtCurrency;

    public DebtCollectionCaseResponse() {}

    public DebtCollectionCaseResponse(DebtCollectionCase dbCase) {
        id = dbCase.getId();
        customer = new CustomerResponse(dbCase.getCustomer());
        status = dbCase.getStatus();
        dueDate = dbCase.getDueDate();
        paidDate = dbCase.getPaidDate();
        setAmount(dbCase.getAmount());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public Money getAmount() {
        return Money.of(debtAmount, debtCurrency);
    }

    public void setAmount(Money amount) {
        this.debtAmount = amount.getNumberStripped();
        this.debtCurrency = amount.getCurrency().getCurrencyCode();
    }

    public BigDecimal getDebtAmount() {
        return debtAmount;
    }

    public void setDebtAmount(BigDecimal debtAmount) {
        this.debtAmount = debtAmount;
    }

    public String getDebtCurrency() {
        return debtCurrency;
    }

    public void setDebtCurrency(String debtCurrency) {
        this.debtCurrency = debtCurrency;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public CustomerResponse getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = new CustomerResponse(customer);
    }
}
