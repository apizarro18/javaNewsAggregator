package com.alexpizarro.javaAggregator.news.controller;

import com.alexpizarro.javaAggregator.news.model.NewsResponse;
import com.alexpizarro.javaAggregator.news.model.User;
import com.alexpizarro.javaAggregator.news.service.NewsService;
import org.springframework.web.bind.annotation.*;
import com.alexpizarro.javaAggregator.news.service.UserService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService){
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Spring boot is finally working! You're live.";
    }


    @PostMapping("/api/users")
    public User createUser(@RequestBody User user){
        System.out.println("Creating user: " + user.getUsername());
        return userService.saveUser(user);
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
