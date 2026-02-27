package com.alexpizarro.javaAggregator.news.repository;
import com.alexpizarro.javaAggregator.news.model.User;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    //Does User Exist?
    boolean existsById(long id);
    boolean existsByUsername(String username);

    //Find User
    Optional<User> findById(long id);
    Optional<User> findByUsername(String username);
}
