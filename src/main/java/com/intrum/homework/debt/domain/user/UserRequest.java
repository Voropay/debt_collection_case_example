package com.intrum.homework.debt.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserRequest implements User{
    private Long id;
    private String username;
    private String email;
    private String password;

    @JsonIgnore
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("UserDto [username=").append(username).append(", password=").append(password).append(", email=").append(email).append("]");
        return builder.toString();
    }
}
