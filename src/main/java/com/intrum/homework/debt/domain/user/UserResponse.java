package com.intrum.homework.debt.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserResponse implements User {

    private Long id;
    private String username;
    private String email;

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

    @JsonIgnore
    public String getPassword() {
        return "";
    }

    public void setPassword(final String password) {}
}
