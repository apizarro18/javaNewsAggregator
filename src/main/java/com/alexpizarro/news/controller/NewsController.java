package com.alexpizarro.news.controller;

import com.alexpizarro.news.model.NewsResponse;
import com.alexpizarro.news.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/api/news")
    public NewsResponse grabNews(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String keywords,
            @RequestParam(required = false) String category
    ){
        return newsService.fetchNews(country, keywords, category);
    }

}
