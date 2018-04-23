package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.customer.CustomerEntity;

public interface CustomerService {
    CustomerEntity findByPersonalId(String personalId);
    CustomerEntity create(Customer customer);
}
