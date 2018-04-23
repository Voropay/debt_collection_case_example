package com.intrum.homework.debt.domain.debtcollectioncase;

import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.customer.CustomerEntity;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.spi.DefaultNumberValue;

import javax.money.NumberValue;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class DebtCollectionCaseEntity extends DebtCollectionCase {

    @Version
    Long version = 1L;

    private BigDecimal debtAmount;
    private String debtCurrency;

    @Id
    @GeneratedValue
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public Long getOwnerId() {
        return super.getOwnerId();
    }

    public void setOwnerId(Long ownerId) {
        super.setOwnerId(ownerId);
    }

    @Transient
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
        return super.getStatus();
    }

    public void setStatus(int status) {
        super.setStatus(status);
    }

    public Date getDueDate() {
        return super.getDueDate();
    }

    public void setDueDate(Date dueDate) {
        super.setDueDate(dueDate);
    }

    public Date getPaidDate() {
        return super.getPaidDate();
    }

    public void setPaidDate(Date paidDate) {
        super.setPaidDate(paidDate);
    }

    @ManyToOne(targetEntity = CustomerEntity.class)
    @JoinColumn(name="customerId")
    public Customer getCustomer() {
        return super.getCustomer();
    }

    public void setCustomer(Customer customer) {
        super.setCustomer(customer);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

}
