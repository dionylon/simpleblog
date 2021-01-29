package com.dionysun.simpleblog.controller;

import com.dionysun.simpleblog.common.PassToken;
import com.dionysun.simpleblog.search.ArticleDoc;
import com.dionysun.simpleblog.service.ArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/search")
public class SearchController {
    @Qualifier("SearchServiceImpl")
    @Autowired
    private ArticleSearchService searchService;
    @PassToken
    @GetMapping()
    public ResponseEntity<SearchHits<ArticleDoc>> search(@RequestParam("q")String keywords, Pageable pageable){
        SearchHits<ArticleDoc> searchHits = searchService.search(keywords, pageable);
        return ResponseEntity.ok(searchHits);
    }
}
