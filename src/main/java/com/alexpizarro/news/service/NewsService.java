package com.alexpizarro.news.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import com.alexpizarro.news.model.Article;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;
import com.alexpizarro.news.model.NewsResponse;
import com.alexpizarro.news.model.Article;
import org.springframework.web.util.UriComponentsBuilder;


@Service
public class NewsService {
    @Value("${news.api.key}")
    private String apiKey;
    private String url;
    private Map<String, NewsResponse> responses;

    public NewsResponse fetchNews(String country, String keywords, String category){

        /**
         * Pulls top stories according the given arguments.
         */

        url = "https://newsapi.org/v2/top-headlines?";

        //Create URI from the given arguments.
        URI link = UriComponentsBuilder.fromUriString(url)
                .queryParam("country", country)
                .queryParam("q", keywords)
                .queryParam("category", category)
                .queryParam("apiKey", apiKey)
                .build()
                .toUri();

        //Start restClient
        RestClient restClient = RestClient.create();

        //Send GET to NewsAPI to pull requested news.
        NewsResponse response = restClient.get()
                .uri(link)
                .retrieve()
                .body(NewsResponse.class);

        return response;
    }

    public Article pullArticle(NewsResponse response){
        /**
         * Returns the list of articles from a given NewsResponse response.
          */

    }
}

