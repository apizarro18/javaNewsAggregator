package com.alexpizarro.javaAggregator.news.controller;

import com.alexpizarro.javaAggregator.news.model.NewsResponse;
import com.alexpizarro.javaAggregator.news.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService){
        this.newsService = newsService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Spring boot is finally working! You're live.";
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
