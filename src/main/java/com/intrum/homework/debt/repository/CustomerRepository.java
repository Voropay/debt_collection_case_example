package com.intrum.homework.debt.repository;

import com.intrum.homework.debt.domain.customer.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByPersonalId(String personalId);
}
