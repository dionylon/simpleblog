package com.dionysun.simpleblog.service;

import com.dionysun.simpleblog.search.ArticleDoc;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

@Service
public interface ArticleSearchService {
    /**
     *
     */
    SearchHits<ArticleDoc> search(String keywords, Pageable pageable);
}
