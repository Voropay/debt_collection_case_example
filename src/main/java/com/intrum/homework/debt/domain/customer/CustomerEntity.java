package com.intrum.homework.debt.domain.customer;

import javax.persistence.*;

@Entity
public class CustomerEntity extends Customer {

    @Version
    Long version = 1L;

    @Id
    @GeneratedValue
    public Long getId() {
        return super.getId();
    }

    public void setId(Long id) {
        super.setId(id);
    }

    public String getFirstName() {
        return super.getFirstName();
    }

    public void setFirstName(String firstName) {
        super.setFirstName(firstName);
    }

    public String getLastName() {
        return super.getLastName();
    }

    public void setLastName(String lastName) {
        super.setLastName(lastName);
    }

    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    @Column(unique = true)
    public String getPersonalId() {
        return super.getPersonalId();
    }

    public void setPersonalId(String personalId) {
        super.setPersonalId(personalId);
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }




}
