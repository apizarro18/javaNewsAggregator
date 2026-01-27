package com.alexpizarro.news.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import org.springframework.stereotype.Service;
import org.springframework. context.annotation.Bean;
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
    private HashMap<String, String> queries = new HashMap<>();
    private int queryCount;
    private final RestClient restClient;

    public NewsService(RestClient restClient){
        this.restClient = restClient;
    }

    public NewsResponse fetchNews(String country, String keywords, String category){

        /**
         * Pulls top stories according the given arguments.
         */

        url = "https://newsapi.org/v2/top-headlines";

        //Create URI from the given arguments.
        URI link = UriComponentsBuilder.fromUriString(url)
                .queryParam("country", country)
                .queryParam("q", keywords)
                .queryParam("category", category)
                .queryParam("apiKey", apiKey)
                .build()
                .toUri();

        //Check created query to make sure the query hasn't already been made.
        String storedLink = link.toString();

        if (!(queries.containsValue(storedLink))){
            queries.put("Query:", storedLink);
        }
        else{
            //If query has already been made, immediately return to ignore calling the API.
            return null;
        }

        //Send GET to NewsAPI to pull requested news.
        NewsResponse response = restClient.get()
                .uri(link)
                .retrieve()
                .body(NewsResponse.class);

        return response;
    }
}

