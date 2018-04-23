package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.customer.CustomerEntity;
import com.intrum.homework.debt.domain.debtcollectioncase.*;
import com.intrum.homework.debt.domain.user.User;
import com.intrum.homework.debt.domain.user.UserRequest;
import com.intrum.homework.debt.repository.DebtCollectionCaseRepository;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class DebtCollectionCaseServiceTests {

    @Autowired
    private DebtCollectionCaseService debtService;

    @Autowired
    private DebtCollectionCaseRepository debtRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Test
    public void testCreation() {
        //test debt case creation
        DebtCollectionCaseRequest debtCollectionCaseRequest = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest.setAmount(Money.of(100, "EUR"));
        debtCollectionCaseRequest.setDueDate(new Date());
        Customer customer = new Customer();
        customer.setPersonalId("1234");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("ccc@bbb.com");
        debtCollectionCaseRequest.setCustomer(customer);

        UserRequest user = new UserRequest();
        user.setEmail("aaa@bbb.com");
        user.setUsername("aaa");
        user.setPassword("password");
        userService.registerNewUserAccount(user);
        User dbUser = userService.findByUsername(user.getUsername());

        DebtCollectionCase dcCase = debtService.create(debtCollectionCaseRequest, user);
        CustomerEntity dbCustomer = customerService.findByPersonalId(customer.getPersonalId());

        assertThat(dcCase.getOwnerId(), equalTo(dbUser.getId()));
        assertThat(dcCase.getCustomer().getId(), equalTo(dbCustomer.getId()));
        assertThat(dcCase.getAmount(), equalTo(Money.of(100, "EUR")));
        assertThat(dcCase.getStatus(), equalTo(DebtCollectionCaseStatus.INPROCESS.status()));
        assertThat(dcCase.getDueDate(), equalTo(debtCollectionCaseRequest.getDueDate()));
        assertThat(dcCase.getPaidDate(), is(nullValue()));
    }

    @Test
    public void testGetByUsername() {

        //create 3 debt cases for 2 users
        UserRequest user1 = new UserRequest();
        user1.setEmail("user1@bbb.com");
        user1.setUsername("user1");
        user1.setPassword("password");
        userService.registerNewUserAccount(user1);
        User dbUser1 = userService.findByUsername(user1.getUsername());

        UserRequest user2 = new UserRequest();
        user2.setEmail("user2@bbb.com");
        user2.setUsername("user2");
        user2.setPassword("password");
        userService.registerNewUserAccount(user2);
        User dbUser2 = userService.findByUsername(user2.getUsername());

        DebtCollectionCaseRequest debtCollectionCaseRequest1 = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest1.setAmount(Money.of(100, "EUR"));
        debtCollectionCaseRequest1.setDueDate(new Date());
        Customer customer1 = new Customer();
        customer1.setPersonalId("1111");
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("doe@bbb.com");
        debtCollectionCaseRequest1.setCustomer(customer1);
        DebtCollectionCase dcCase1 = debtService.create(debtCollectionCaseRequest1, user1);

        DebtCollectionCaseRequest debtCollectionCaseRequest2 = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest2.setAmount(Money.of(200, "EUR"));
        debtCollectionCaseRequest2.setDueDate(new Date());
        Customer customer2 = new Customer();
        customer2.setPersonalId("2222");
        customer2.setFirstName("Jack");
        customer2.setLastName("Smith");
        customer2.setEmail("smith@bbb.com");
        debtCollectionCaseRequest2.setCustomer(customer2);
        DebtCollectionCase dcCase2 = debtService.create(debtCollectionCaseRequest2, user2);

        DebtCollectionCaseRequest debtCollectionCaseRequest3 = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest3.setAmount(Money.of(300, "EUR"));
        debtCollectionCaseRequest3.setDueDate(new Date());
        Customer customer3 = new Customer();
        customer3.setPersonalId("1234");
        customer3.setFirstName("John");
        customer3.setLastName("Doe");
        customer3.setEmail("ccc@bbb.com");
        debtCollectionCaseRequest3.setCustomer(customer3);
        DebtCollectionCase dcCase3 = debtService.create(debtCollectionCaseRequest3, user2);

        //test that each user will get only its own debt cases
        List<DebtCollectionCaseEntity> debtCases1 = debtService.findByOwnerId(dbUser1.getId());
        assertThat(debtCases1, is(notNullValue()));
        assertThat(debtCases1.size(), equalTo(1));
        assertThat(debtCases1.get(0).getAmount(), equalTo(debtCollectionCaseRequest1.getAmount()));
        assertThat(debtCases1.get(0).getCustomer().getPersonalId(), equalTo(customer1.getPersonalId()));

        List<DebtCollectionCaseEntity> debtCases2 = debtService.findByOwnerId(dbUser2.getId());
        assertThat(debtCases2, is(notNullValue()));
        assertThat(debtCases2.size(), equalTo(2));

        DebtCollectionCaseEntity firstResult = debtCases2.stream()
                .filter(x -> x.getCustomer().getPersonalId().equals(customer2.getPersonalId()))
                .findFirst().orElse(null);
        assertThat(firstResult.getAmount(), equalTo(debtCollectionCaseRequest2.getAmount()));

        DebtCollectionCaseEntity secondResult = debtCases2.stream()
                .filter(x -> x.getCustomer().getPersonalId().equals(customer3.getPersonalId()))
                .findFirst().orElse(null);
        assertThat(debtCases2.get(1).getAmount(), equalTo(debtCollectionCaseRequest3.getAmount()));
    }

    @Test
    public void testUpdateStatus() {
        //test status updating to PAID
        UserRequest user1 = new UserRequest();
        user1.setEmail("user3@bbb.com");
        user1.setUsername("user3");
        user1.setPassword("password");
        userService.registerNewUserAccount(user1);
        User dbUser1 = userService.findByUsername(user1.getUsername());

        DebtCollectionCaseRequest debtCollectionCaseRequest1 = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest1.setAmount(Money.of(100, "EUR"));
        debtCollectionCaseRequest1.setDueDate(new Date());
        Customer customer1 = new Customer();
        customer1.setPersonalId("1111");
        customer1.setFirstName("John");
        customer1.setLastName("Doe");
        customer1.setEmail("doe@bbb.com");
        debtCollectionCaseRequest1.setCustomer(customer1);
        DebtCollectionCase dcCase1 = debtService.create(debtCollectionCaseRequest1, dbUser1);

        DebtCollectionCaseStatusRequest statusRequest1 = new DebtCollectionCaseStatusRequest();
        statusRequest1.setId(dcCase1.getId());
        statusRequest1.setStatus(DebtCollectionCaseStatus.PAID.status());
        statusRequest1.setPaidDate(new Date());
        DebtCollectionCase dcCase1upd = debtService.updateStatus(statusRequest1, dbUser1);
        assertThat(dcCase1upd.getId(), equalTo(statusRequest1.getId()));
        assertThat(dcCase1upd.getStatus(), equalTo(statusRequest1.getStatus()));
        assertThat(dcCase1upd.getPaidDate(), equalTo(statusRequest1.getPaidDate()));

        //test status updating to DEFAULTED
        DebtCollectionCaseRequest debtCollectionCaseRequest2 = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest2.setAmount(Money.of(200, "EUR"));
        debtCollectionCaseRequest2.setDueDate(new Date());
        debtCollectionCaseRequest2.setCustomer(customer1);
        DebtCollectionCase dcCase2 = debtService.create(debtCollectionCaseRequest2, dbUser1);

        DebtCollectionCaseStatusRequest statusRequest2 = new DebtCollectionCaseStatusRequest();
        statusRequest2.setId(dcCase2.getId());
        statusRequest2.setStatus(DebtCollectionCaseStatus.DEFAULTED.status());
        DebtCollectionCase dcCase2upd = debtService.updateStatus(statusRequest2, dbUser1);
        assertThat(dcCase2upd.getId(), equalTo(statusRequest2.getId()));
        assertThat(dcCase2upd.getStatus(), equalTo(statusRequest2.getStatus()));
        assertThat(dcCase2upd.getPaidDate(), is(nullValue()));

        //test that status will not be updated for wrong user
        UserRequest user2 = new UserRequest();
        user2.setEmail("user4@bbb.com");
        user2.setUsername("user4");
        user2.setPassword("password");
        userService.registerNewUserAccount(user2);
        User dbUser2 = userService.findByUsername(user2.getUsername());

        DebtCollectionCaseRequest debtCollectionCaseRequest3 = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest3.setAmount(Money.of(300, "EUR"));
        debtCollectionCaseRequest3.setDueDate(new Date());
        debtCollectionCaseRequest3.setCustomer(customer1);
        DebtCollectionCase dcCase3 = debtService.create(debtCollectionCaseRequest3, dbUser2);

        DebtCollectionCaseStatusRequest statusRequest3 = new DebtCollectionCaseStatusRequest();
        statusRequest3.setId(dcCase3.getId());
        statusRequest3.setStatus(DebtCollectionCaseStatus.DEFAULTED.status());
        DebtCollectionCase dcCase3upd = debtService.updateStatus(statusRequest3, dbUser1);
        assertThat(dcCase3upd, is(nullValue()));
    }
}
