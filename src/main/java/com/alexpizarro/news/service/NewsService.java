package com.alexpizarro.news.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import com.alexpizarro.news.model.NewsResponse;

@Service
public class NewsService {
    @Value("${news.api.key}")
    private String apiKey;

    public NewsResponse getTopHeadlines(){
        //Build restTemplate to make requests
        RestTemplate restTemplate = new RestTemplate();

        //Define URL
        String url = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + apiKey;

        NewsResponse response = restTemplate.getForObject(url, NewsResponse.class);
        return response;
    }

    public NewsResponse get
}

