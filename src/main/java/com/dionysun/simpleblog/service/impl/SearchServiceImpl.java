package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.search.ArticleDoc;
import com.dionysun.simpleblog.service.ArticleSearchService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

@Service("SearchServiceImpl")
public class SearchServiceImpl implements ArticleSearchService {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;
    @Override
    public SearchHits<ArticleDoc> search(String keywords, Pageable pageable) {
        String[] list = keywords.trim().split(" ");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        for(String keyword : list){
            QueryBuilder builder = QueryBuilders.multiMatchQuery(keyword, "title", "content");
            boolQueryBuilder.should(builder);
        }
        HighlightBuilder.Field highlightField =
                new HighlightBuilder.Field("*")
                        .preTags("<div class=\"hit\">").postTags("</div>")
                        .requireFieldMatch(false);
        Query query = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(pageable)
                .withHighlightFields(highlightField)
                .build();
        // TODO 封装为article的page
        return elasticsearchOperations.search(query, ArticleDoc.class);
    }
}
