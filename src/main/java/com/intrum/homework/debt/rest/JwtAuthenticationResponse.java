package com.intrum.homework.debt.rest;

import java.io.Serializable;

public class JwtAuthenticationResponse implements Serializable {

    private static final long serialVersionUID = 1278418276348127634L;

    private String token;

    public JwtAuthenticationResponse(String token) {
        this.token = token;
    }

    public JwtAuthenticationResponse() {}

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}