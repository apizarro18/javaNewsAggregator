package com.alexpizarro.javaAggregator.news.config;

import org.springframework. context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ClientConfig {

    @Bean
    public RestClient restClient(){
        return RestClient.create();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {return new BCryptPasswordEncoder();}
}
