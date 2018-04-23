package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.customer.CustomerEntity;
import com.intrum.homework.debt.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public CustomerEntity findByPersonalId(String personalId) {
        return customerRepository.findByPersonalId(personalId);
    }

    public CustomerEntity create(Customer customer) {
        CustomerEntity customerEntity = findByPersonalId(customer.getPersonalId());
        if(customerEntity == null) {
            customerEntity = new CustomerEntity();
            customerEntity.setEmail(customer.getEmail());
            customerEntity.setFirstName(customer.getFirstName());
            customerEntity.setLastName(customer.getLastName());
            customerEntity.setPersonalId(customer.getPersonalId());
            customerRepository.save(customerEntity);
        }
        return customerEntity;
    }
}
