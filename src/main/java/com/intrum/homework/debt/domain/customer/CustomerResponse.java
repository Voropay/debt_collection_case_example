package com.intrum.homework.debt.domain.customer;

public class CustomerResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String personalId;

    public CustomerResponse() {}

    public CustomerResponse(Customer customer) {
        id = customer.getId();
        firstName = customer.getFirstName();
        lastName = customer.getLastName();
        email = customer.getEmail();
        personalId = customer.getPersonalId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }
}
