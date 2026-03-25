package com.alexpizarro.javaAggregator.news.model;

public class AuthResponse {
    private String token;

    public AuthResponse(String token){
        this.token = token;
    }

    public String getToken(){
        return this.token;
    }
}

