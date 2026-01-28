package com.alexpizarro.news.repository;

import com.alexpizarro.news.model.Article;
import com.alexpizarro.news.model.NewsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    boolean existsByUrl(String url);

    default void addUniqueArticle(Article article){
        if (!existsByUrl(article.getUrl())){
            save(article);
        }
    }
}
