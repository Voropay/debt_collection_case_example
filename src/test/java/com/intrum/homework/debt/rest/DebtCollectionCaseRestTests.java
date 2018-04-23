package com.intrum.homework.debt.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intrum.homework.debt.domain.customer.Customer;
import com.intrum.homework.debt.domain.debtcollectioncase.*;
import com.intrum.homework.debt.domain.user.UserRequest;
import com.intrum.homework.debt.security.JwtTokenUtil;
import com.intrum.homework.debt.service.UserService;
import org.javamoney.moneta.Money;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class DebtCollectionCaseRestTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Test
    public void testCreation() throws Exception {
        //create user and auth token
        UserRequest user = new UserRequest();
        user.setEmail("aaa@bbb.com");
        user.setUsername("aaa");
        user.setPassword("password");
        userService.registerNewUserAccount(user);
        String token = jwtTokenUtil.generateToken(user.getUsername());

        //create debt case with attached customer info
        DebtCollectionCaseRequest debtCollectionCaseRequest = new DebtCollectionCaseRequest();
        debtCollectionCaseRequest.setAmount(Money.of(100, "EUR"));
        debtCollectionCaseRequest.setDueDate(new Date());
        Customer customer = new Customer();
        customer.setPersonalId("1234");
        customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setEmail("ccc@bbb.com");
        debtCollectionCaseRequest.setCustomer(customer);

        //test debt case creation
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<DebtCollectionCaseRequest> createRequest = new HttpEntity<>(debtCollectionCaseRequest, headers);
        ResponseEntity<String> createResponse = restTemplate.exchange("/dccase/create", HttpMethod.POST, createRequest, String.class);
        assertThat(createResponse.getStatusCode(), equalTo(HttpStatus.OK));
        String createBody = createResponse.getBody();
        assertThat(createBody, equalTo("created"));

        //test debt case receiving
        HttpEntity<String> getRequest = new HttpEntity<>("", headers);
        ResponseEntity<String> getResponse = restTemplate.exchange("/dccase/get", HttpMethod.GET, getRequest, String.class);
        assertThat(getResponse.getStatusCode(), equalTo(HttpStatus.OK));
        Collection<DebtCollectionCaseResponse> getBody = new ObjectMapper().readValue(getResponse.getBody(), new TypeReference<Collection<DebtCollectionCaseResponse>>() { });
        assertThat(getBody, is(notNullValue()));
        DebtCollectionCaseResponse dcCase = getBody.iterator().next();
        assertThat(dcCase.getAmount(), equalTo(Money.of(100, "EUR")));
        assertThat(dcCase.getDueDate(), equalTo(debtCollectionCaseRequest.getDueDate()));
        assertThat(dcCase.getPaidDate(), is(nullValue()));
        assertThat(dcCase.getId(), is(notNullValue()));
        assertThat(dcCase.getStatus(), equalTo(DebtCollectionCaseStatus.INPROCESS.status()));
        assertThat(dcCase.getCustomer().getPersonalId(), equalTo(customer.getPersonalId()));
        assertThat(dcCase.getCustomer().getFirstName(), equalTo(customer.getFirstName()));
        assertThat(dcCase.getCustomer().getLastName(), equalTo(customer.getLastName()));
        assertThat(dcCase.getCustomer().getEmail(), equalTo(customer.getEmail()));
        assertThat(dcCase.getCustomer().getId(), is(notNullValue()));

        //test debt case status update
        DebtCollectionCaseStatusRequest statusRequest = new DebtCollectionCaseStatusRequest();
        statusRequest.setId(dcCase.getId());
        statusRequest.setStatus(DebtCollectionCaseStatus.PAID.status());
        statusRequest.setPaidDate(new Date());
        HttpEntity<DebtCollectionCaseStatusRequest> updateRequest = new HttpEntity<>(statusRequest, headers);
        ResponseEntity<String> updateResponse = restTemplate.exchange("/dccase/updatestatus", HttpMethod.POST, updateRequest, String.class);
        assertThat(updateResponse.getStatusCode(), equalTo(HttpStatus.OK));
        String updateBody = updateResponse.getBody();
        assertThat(updateBody, equalTo("updated"));

        //get debt case and check that status is updated
        HttpEntity<String> getRequest1 = new HttpEntity<>("", headers);
        ResponseEntity<String> getResponse1 = restTemplate.exchange("/dccase/get", HttpMethod.GET, getRequest1, String.class);
        assertThat(getResponse1.getStatusCode(), equalTo(HttpStatus.OK));
        Collection<DebtCollectionCaseResponse> getBody1 = new ObjectMapper().readValue(getResponse1.getBody(), new TypeReference<Collection<DebtCollectionCaseResponse>>() { });
        DebtCollectionCaseResponse dcCase1 = getBody1.iterator().next();
        assertThat(dcCase1.getStatus(), equalTo(DebtCollectionCaseStatus.PAID.status()));
        assertThat(dcCase1.getPaidDate(), equalTo(statusRequest.getPaidDate()));
    }
}
