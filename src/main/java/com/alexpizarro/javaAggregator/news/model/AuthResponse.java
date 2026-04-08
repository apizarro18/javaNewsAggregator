package com.alexpizarro.javaAggregator.news.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {

    @JsonProperty("token")
    private String token;

    public AuthResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}

