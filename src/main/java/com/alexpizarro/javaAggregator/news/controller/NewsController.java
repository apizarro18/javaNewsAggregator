package com.alexpizarro.javaAggregator.news.controller;

import com.alexpizarro.javaAggregator.news.model.NewsResponse;
import com.alexpizarro.javaAggregator.news.model.User;
import com.alexpizarro.javaAggregator.news.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.alexpizarro.javaAggregator.news.service.UserService;
import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService){
        this.newsService = newsService;
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidExceptions(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        return errors;
    }

    @GetMapping("/hello")
    public String sayHello(){
        return "Spring boot is finally working! You're live.";
    }

    @PostMapping("/api/users")
    public User createUser(@Valid @RequestBody User user){
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
