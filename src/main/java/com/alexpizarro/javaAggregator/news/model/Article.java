package com.alexpizarro.javaAggregator.news.model;


import java.util.Objects;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "query_id")
    @Getter
    @Setter
    private Query query;

    @Getter
    @Setter
    private String author;

    @Getter
    @Setter
    private String title;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String urlToImage;

    @Getter
    @Setter
    private String publishedAt;

    @Getter
    @Setter
    private String content;

    public Article() {
    }

    //Override functions:
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(author, article.author) && Objects.equals(title, article.title) && Objects.equals(description, article.description) && Objects.equals(url, article.url) && Objects.equals(urlToImage, article.urlToImage) && Objects.equals(publishedAt, article.publishedAt) && Objects.equals(content, article.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, title, description, url, urlToImage, publishedAt, content);
    }
}

