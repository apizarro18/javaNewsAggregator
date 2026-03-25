package com.alexpizarro.javaAggregator.news.service;
import com.alexpizarro.javaAggregator.news.model.User;
import com.alexpizarro.javaAggregator.news.repository.UserRepository;
import com.alexpizarro.javaAggregator.news.model.Topic;
import com.alexpizarro.javaAggregator.news.repository.TopicRepository;
import io.jsonwebtoken.security.Keys;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import io.jsonwebtoken.Jwts;

import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private final String SECRET_KEY = "d038981187f4f1c7d7a54031b408505b2f1962cb5c52969d3ba66dec138302bd";
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, TopicRepository topicRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.topicRepository = topicRepository;
    }

    //USER CREATION

    private String hashPassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        return encodedPassword;
    }

    public boolean verifyUser(String rawPassword, String username){
        return userRepository.findByUsername(username)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    public User saveUser(User user){
        if(userRepository.existsByUsername(user.getUsername())){
            throw new RuntimeException("Username has been taken!");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new RuntimeException("Email is in use!");
        }

        String encodedPassword = hashPassword(user);
        user.setPassword(encodedPassword);

        return userRepository.save(user);

    }

    //USER TOKENS
    public String generateToken(String username){
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(Keys.hmacShaKeyFor(SECRET_KEY.getBytes()))
                .compact();
    }

    //USER <-> TOPIC INTERACTION

    @Transactional
    public void followTopic(Long userID, String topicName){

        User selectedUser;
        Topic selectedTopic;

        //1. Get User from UserRepository
        Optional<User> findUser = userRepository.findById(userID);
        if(findUser.isEmpty()){
            throw new RuntimeException("User not found!");
        }
        else{
            selectedUser = findUser.get();
        }

        //2. Get Topic
        Optional<Topic> findTopic = topicRepository.findByTopicName(topicName);
        if(findTopic.isEmpty()){
            Topic newTopic = new Topic();
            newTopic.setTopicName(topicName);
            selectedTopic = topicRepository.save(newTopic);
        }
        else{
            selectedTopic = findTopic.get();
        }

        //3. Add Topic to User's set.
        selectedUser.addTopic(selectedTopic);

        //4. Add User to Topic's set.
        selectedTopic.addUser(selectedUser);

        //5. Save the User
        userRepository.save(selectedUser);
    }



}
