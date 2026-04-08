package com.alexpizarro.javaAggregator.news.controller;

import com.alexpizarro.javaAggregator.news.model.AuthResponse;
import com.alexpizarro.javaAggregator.news.model.LoginRequest;
import com.alexpizarro.javaAggregator.news.model.NewsResponse;
import com.alexpizarro.javaAggregator.news.model.User;
import com.alexpizarro.javaAggregator.news.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        boolean valid = userService.verifyUser(loginRequest.getPassword(), loginRequest.getUsername());

        if(valid){
            String token = userService.generateToken(loginRequest.getUsername());
            return ResponseEntity.ok(new AuthResponse(token));
        }
        else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credentials"));
        }
    }

    @PostMapping("/api/users")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user){
        //Debug Line to test that users are getting created
        System.out.println("Creating user: " + user.getUsername());

        //Save plaintext password to pass it to Login.
        String plaintextPassword = user.getPassword();

        //User is saved to NeonDB database.
        userService.saveUser(user);

        //Users are automatically logged in, so we create a login request and send it to Login().
        LoginRequest autoLogin = new LoginRequest();
        autoLogin.setPassword(plaintextPassword);
        autoLogin.setUsername(user.getUsername());
        ResponseEntity<?> response = login(autoLogin);

        return response;
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
