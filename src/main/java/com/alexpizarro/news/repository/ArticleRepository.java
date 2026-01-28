package com.alexpizarro.news.repository;

import com.alexpizarro.news.model.Article;
import com.alexpizarro.news.model.NewsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    //Does the Article exist?
    boolean existsByUrl(String url);

    //Return the Article
    Article findArticleByTitle(String title);
    Article findArticleByUrl(String url);

    //Delete the Article
    void deleteByUrl(String url);
}
