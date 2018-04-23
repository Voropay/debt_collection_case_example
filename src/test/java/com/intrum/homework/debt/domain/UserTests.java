package com.intrum.homework.debt.domain;

import com.intrum.homework.debt.domain.user.UserEntity;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class UserTests {

    @Test
    public void testEmail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(" aaa@bbb.com ");
        assertThat(userEntity.getEmail(), equalTo("aaa@bbb.com"));
    }

    @Test
    public void testUsername() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testuser");
        assertThat(userEntity.getUsername(), equalTo("testuser"));
    }

    @Test
    public void testPassword() {
        UserEntity userEntity = new UserEntity();
        userEntity.setPassword("password");
        assertThat(userEntity.getPassword(), equalTo("password"));
    }


}
