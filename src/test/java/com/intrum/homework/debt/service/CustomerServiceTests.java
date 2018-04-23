package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.customer.CustomerEntity;
import com.intrum.homework.debt.domain.user.UserEntity;
import com.intrum.homework.debt.domain.user.UserRequest;
import com.intrum.homework.debt.repository.CustomerRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class CustomerServiceTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testCreation() {
        //create cutomer
        assertThat(customerService, is(notNullValue()));
        Customer customer = new Customer();
        customer.setEmail("ccc@bbb.com");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setPersonalId("1234");
        customerService.create(customer);
        CustomerEntity dbCustomer = customerService.findByPersonalId(customer.getPersonalId());

        //check that creation of customer with existing personal id returns existing profile
        assertThat(dbCustomer, is(notNullValue()));
        assertThat(dbCustomer.getId(), is(notNullValue()));
        assertThat(customer.getFirstName(), equalTo(dbCustomer.getFirstName()));
        assertThat(customer.getLastName(), equalTo(dbCustomer.getLastName()));
        assertThat(customer.getEmail(), equalTo(dbCustomer.getEmail()));
        assertThat(customer.getPersonalId(), equalTo(dbCustomer.getPersonalId()));

        CustomerEntity anotherDbCustomer = customerService.create(customer);
        assertThat(anotherDbCustomer.getId(), equalTo(dbCustomer.getId()));
    }
}
