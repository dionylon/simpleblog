package com.dionysun.simpleblog.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleDocRepository extends ElasticsearchRepository<ArticleDoc, Integer> {
    Page<ArticleDoc> findByTitle(String title, Pageable pageable);
}
