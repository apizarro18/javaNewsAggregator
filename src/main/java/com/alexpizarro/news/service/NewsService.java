package com.alexpizarro.news.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import com.alexpizarro.news.repository.ArticleRepository;
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
    private HashSet<String> queriesMap = new HashSet<>();
    private HashMap<String, Article> articleMap = new HashMap<>();
    private int queryCount;
    private final RestClient restClient;
    private final ArticleRepository articleRepository;




    public NewsService(RestClient restClient, ArticleRepository articleRepository){
        this.restClient = restClient;
        this.articleRepository = articleRepository;
    }

    /**
     *
     * Pulls top stories according to the given parameters.
     * @param country
     * @param keywords
     * @param category
     * @return returns a NewsResponse object if the query does not exist in the queriesMap. If it does,
     * the function returns null.
     */
    public NewsResponse fetchNews(String country, String keywords, String category){
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
        String storedQuery = link.toASCIIString();

        if (queriesMap.contains(storedQuery)){
            //If query is already been made, return nothing.
            return null;
        }
        else{
            //If query is new, add query to the map.
            queriesMap.add(storedQuery);
        }

        //Send GET to NewsAPI to pull requested news.
        NewsResponse response = restClient.get()
                .uri(link)
                .retrieve()
                .body(NewsResponse.class);

        return response;
    }

    /**
     *
     * @param response
     * Pulls all the articles from a given response and adds the new, unique entries to the articlesMap.
     */
    public void updateArticleMap(NewsResponse response){
        if (response == null){return;}

        List<Article> articles = response.getArticles();
        for(Article article: articles){
            if(articleMap.containsKey(article.getUrl())){
                continue;
            }
            else{
                articleMap.put(article.getUrl(), article);
            }
        }
    }

}

