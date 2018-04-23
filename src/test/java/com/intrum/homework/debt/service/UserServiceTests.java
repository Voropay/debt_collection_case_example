package com.intrum.homework.debt.service;

import com.intrum.homework.debt.domain.user.UserEntity;
import com.intrum.homework.debt.repository.UserRepository;
import com.intrum.homework.debt.domain.user.UserRequest;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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
public class UserServiceTests {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Rule
    public ExpectedException exceptionGrabber = ExpectedException.none();

    @Test
    public void testRegistration() {

        //test user account creation
        assertThat(userService, is(notNullValue()));
        UserRequest user = new UserRequest();
        user.setEmail("aaa@bbb.com");
        user.setUsername("aaa");
        user.setPassword("password");
        userService.registerNewUserAccount(user);
        UserEntity dbUser = userRepository.findByUsername(user.getUsername());
        assertThat(dbUser, is(notNullValue()));
        assertThat(user.getUsername(), equalTo(dbUser.getUsername()));
        assertThat(user.getEmail(), equalTo(dbUser.getEmail()));
        assertThat(user.getPassword(), not(equalTo(dbUser.getPassword())));

        //test that user with existing username will not be created
        UserRequest existingUser = new UserRequest();
        existingUser.setEmail("aaa1@bbb.com");
        existingUser.setUsername("aaa");
        existingUser.setPassword("password1");

        exceptionGrabber.expect(UserRegistrationException.class);
        exceptionGrabber.expectMessage("username already exist");
        userService.registerNewUserAccount(existingUser);
    }


}
