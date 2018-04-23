package com.intrum.homework.debt.domain.debtcollectioncase;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.intrum.homework.debt.domain.customer.Customer;
import org.javamoney.moneta.Money;

import java.math.BigDecimal;
import java.util.Date;

public class DebtCollectionCaseRequest extends DebtCollectionCase {

    private BigDecimal debtAmount;
    private String debtCurrency;

    public Customer getCustomer() {
        return super.getCustomer();
    }

    public void setCustomer(Customer customer) {
        super.setCustomer(customer);
    }

    @Override
    @JsonIgnore
    public int getStatus() {
        return DebtCollectionCaseStatus.INPROCESS.status();
    }

    @Override
    @JsonIgnore
    public Date getPaidDate() {
        return null;
    }

    @Override
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
}
