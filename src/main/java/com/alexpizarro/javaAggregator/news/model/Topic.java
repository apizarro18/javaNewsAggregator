package com.alexpizarro.javaAggregator.news.model;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name= "Topics")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Setter
    @Getter
    private String topicName;

    @Setter
    @Getter
    @ManyToMany(mappedBy = "followedTopics")
    private Set<User> followedByUsers = new HashSet<>();

    public void addUser(User user){
        this.followedByUsers.add(user);
    }

}
