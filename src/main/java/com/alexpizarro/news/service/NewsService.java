package com.alexpizarro.news.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.alexpizarro.news.model.Query;
import com.alexpizarro.news.repository.ArticleRepository;
import com.alexpizarro.news.repository.QueryRepository;
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
    private final RestClient restClient;
    private final ArticleRepository articleRepository;
    private final QueryRepository queryRepository;

    public NewsService(RestClient restClient, ArticleRepository articleRepository, QueryRepository queryRepository){
        this.restClient = restClient;
        this.articleRepository = articleRepository;
        this.queryRepository = queryRepository;
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
        URI link = UriComponentsBuilder.fromUriString(url)
                .queryParam("country", country)
                .queryParam("q", keywords)
                .queryParam("category", category)
                .queryParam("apiKey", apiKey)
                .build()
                .toUri();

        //1. Check cache to see if response exist.
        if(queryRepository.existsByUri(link)){
            Query cachedQuery = queryRepository.findByUri(link);
            List<Article> cachedArticles = cachedQuery.getArticles();
            NewsResponse cachedResponse = convertToResponse(cachedArticles);
            return cachedResponse;
        }
        else{
            Query newQuery = new Query();
            newQuery.setUri(link);
            addUniqueQuery(newQuery);
        }

        //2. Prepare new Query.
        Query newQuery = new Query();
        newQuery.setUri(link);
        newQuery.setCountry(country);
        newQuery.setKeywords(keywords);
        newQuery.setCategory(category);

        //3. Call API.
        NewsResponse response = restClient.get()
                .uri(link)
                .retrieve()
                .body(NewsResponse.class);

        //4. Link info from the call to the query & DB.
        if (response != null && response.getArticles() != null){

            //4a.Link Articles to Query
            List<Article> responseArticles = response.getArticles();
            for(Article article : responseArticles){
                newQuery.addArticle(article);
            }

            //4b.Save new Query to the database. (Note: this also saves all linked articles because of Cascade)
            queryRepository.save(newQuery);
        }

        return response;
    }

    /**
     * Converts a list of articles into a NewsResponse object.
     * @param articles
     * @return Returns a new NewsResponse object.
     */
    public NewsResponse convertToResponse(List<Article> articles){
        NewsResponse newResponse = new NewsResponse();
        newResponse.setArticles(articles);
        return newResponse;
    }

    /**
     * Adds a query to the database if it doesn't already exist.
     * @param query
     */
    public void addUniqueQuery(Query query){
        if(!queryRepository.existsByUri(query.getUri())){
            queryRepository.save(query);
        }
    }

    /**
     * Links a list of articles to a query and saves them to the repository.
     * @param articles The list of articles from the API.
     * @param query The query that found these articles.
     */
    public void addUniqueArticles(List<Article> articles, Query query) {
        for (Article article : articles) {
            article.setQuery(query);

            //If article does not exist in the database, add it.
            if (!articleRepository.existsByUrl(article.getUrl())) {
                articleRepository.save(article);
            }
        }
    }
}

