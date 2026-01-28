package com.alexpizarro.javaAggregator.news.model;

import java.net.URI;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Query {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "query", cascade = CascadeType.ALL)
    @Getter
    @Setter
    private List<Article> articles;

    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private String country;

    @Getter
    @Setter
    private String keywords;

    @Getter
    @Setter
    private String category;

    @Getter
    @Setter
    private URI uri;

    public Query() {
    }

    public void addArticle(Article article){
        article.setQuery(this);
    }
}
