package com.alexpizarro.javaAggregator.news.repository;

import com.alexpizarro.javaAggregator.news.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByTopicName(String topicName);

}
