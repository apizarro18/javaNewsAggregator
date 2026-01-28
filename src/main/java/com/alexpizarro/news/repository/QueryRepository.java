package com.alexpizarro.news.repository;

import com.alexpizarro.news.model.Article;
import com.alexpizarro.news.model.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import java.net.URI;
import java.util.List;

public interface QueryRepository extends JpaRepository<Query, Long> {

    //Does Query exist?
    boolean existsByUri(URI uri);
    boolean existsByKeywords(String keywords);
    boolean existsByArticles(List<Article> articles);

    //Return existing Query
    Query findByUri(URI uri);
    Query findByArticles(List<Article> articles);

    //Delete existing Query
    void deleteByUri(URI uri);


}
