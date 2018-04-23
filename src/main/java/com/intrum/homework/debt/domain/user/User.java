package com.intrum.homework.debt.domain.user;

public interface User {
    Long getId();
    void setId(Long id);
    String getEmail();
    void setEmail(String email);
    String getUsername();
    void setUsername(String username);
    String getPassword();
    void setPassword(String password);
}
