package com.intrum.homework.debt.rest;

import com.intrum.homework.debt.domain.user.UserRequest;
import com.intrum.homework.debt.domain.user.UserResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class UserRestTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testRegistration() throws Exception {

        //test registration
        UserRequest parameters = new UserRequest();
        parameters.setEmail("test1@aaa.com");
        parameters.setUsername("test1");
        parameters.setPassword("1234");

        HttpEntity<UserRequest> request = new HttpEntity<>(parameters);
        ResponseEntity<String> response = restTemplate.exchange("/user/registration", HttpMethod.POST, request, String.class);

        String responseBody = response.getBody();
        assertThat(responseBody).contains("registered");

        //test login
        JwtAuthRequest authParameters = new JwtAuthRequest();
        authParameters.setUsername("test1");
        authParameters.setPassword("1234");
        HttpEntity<JwtAuthRequest> authRequest = new HttpEntity<>(authParameters);
        ResponseEntity<JwtAuthenticationResponse> authResponse = restTemplate.exchange("/user/login", HttpMethod.POST, authRequest, JwtAuthenticationResponse.class);
        String token = authResponse.getBody().getToken();
        assertThat(token).isNotBlank();

        //test login with wrong credentials
        JwtAuthRequest authWrongParameters = new JwtAuthRequest();
        authWrongParameters.setUsername("test1");
        authWrongParameters.setPassword("zxcv");
        HttpEntity<JwtAuthRequest> authWrongRequest = new HttpEntity<>(authWrongParameters);
        ResponseEntity<String> authWrongResponse = restTemplate.exchange("/user/login", HttpMethod.POST, authWrongRequest, String.class);
        assertThat(authWrongResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);

        //test user account receiving
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        HttpEntity<String> accountRequest = new HttpEntity<>("", headers);
        ResponseEntity<UserResponse> accountResponse = restTemplate.exchange("/user/account", HttpMethod.POST, accountRequest, UserResponse.class);
        assertThat(accountResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        UserResponse user = accountResponse.getBody();
        assertThat(user.getEmail()).isEqualTo(parameters.getEmail());
        assertThat(user.getUsername()).isEqualTo(parameters.getUsername());

        //test request with wrong auth token
        HttpHeaders wrongHeaders = new HttpHeaders();
        wrongHeaders.set("Authorization", "Bearer WRONGTOKEN");
        HttpEntity<String> wrongAccountRequest = new HttpEntity<>("", wrongHeaders);
        ResponseEntity<UserResponse> wrongAccountResponse = restTemplate.exchange("/user/account", HttpMethod.POST, wrongAccountRequest, UserResponse.class);
        assertThat(wrongAccountResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

}
