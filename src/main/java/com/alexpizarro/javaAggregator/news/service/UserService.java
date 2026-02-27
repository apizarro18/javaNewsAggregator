package com.alexpizarro.javaAggregator.news.service;
import com.alexpizarro.javaAggregator.news.model.User;
import com.alexpizarro.javaAggregator.news.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

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

        String encodedPassword = hashPassword(user);
        user.setPassword(encodedPassword);

        return userRepository.save(user);

    }

}
