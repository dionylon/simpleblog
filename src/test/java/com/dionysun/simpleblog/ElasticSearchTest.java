package com.dionysun.simpleblog;

import com.dionysun.simpleblog.search.ArticleDoc;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiTermQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class ElasticSearchTest {
    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchTest.class);
    @Test
    public void testSave(){
        ArticleDoc doc = new ArticleDoc(3, "与富婆交流的十种方法？", "1，富婆......");
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(doc.getId().toString())
                .withObject(doc)
                .build();
        String docId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("article"));
        logger.info("save done. id : " + docId);
    }
    @Test
    public void updateOrSave(){
        ArticleDoc doc = new ArticleDoc(3, "爱、死亡和机器人", "1，xxx......");
        elasticsearchOperations.save(doc, IndexCoordinates.of("article"));
    }
    @Test
    public void getAll(){
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withPageable(PageRequest.of(0, 10))
                .build();
        SearchHits<ArticleDoc> search = elasticsearchOperations.search(query, ArticleDoc.class);
        logger.info(search.toString());
        for(SearchHit<ArticleDoc> hit : search){
            logger.info(hit.toString());
        }
    }
    @Test
    public void testGet(){
        String fuzzyStr = "富婆";
        Criteria criteria = new Criteria("title").contains(fuzzyStr);
        Query query = new CriteriaQuery(criteria);
        SearchHits<ArticleDoc> articleDocSearchHits = elasticsearchOperations.search(
                query,
                ArticleDoc.class,
                IndexCoordinates.of("article"));
        logger.info(articleDocSearchHits.toString());
        for(SearchHit<ArticleDoc> searchHit : articleDocSearchHits.getSearchHits()){
            System.out.println(searchHit);
        }
    }

    @Test
    public void delete(){
        elasticsearchOperations.delete("1", IndexCoordinates.of("article"));
    }
    @Test
    public void testHightlight(){
        String key = "富婆";
        HighlightBuilder.Field highlightField =
                new HighlightBuilder.Field("*")
                .preTags("<pre>").postTags("</pre>")
                .requireFieldMatch(false);
        Query query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(key, "title", "content"))
                .withPageable(PageRequest.of(0, 10))
                .withHighlightFields(highlightField)
                .build();
        SearchHits<ArticleDoc> articleDocSearchHits = elasticsearchOperations.search(query, ArticleDoc.class);
        for(SearchHit<ArticleDoc> hit : articleDocSearchHits){
            logger.info(hit.toString());
        }
    }

    @Test
    public void queryByMultiWord(){
        // 1. 直接将整个输入的字符串作为查询条件
        Query query = new CriteriaQuery(new Criteria("title").matches("爱 机器人"));
        SearchHits<ArticleDoc> search = elasticsearchOperations.search(query, ArticleDoc.class);
        for(SearchHit<ArticleDoc> hit : search){
            logger.info(hit.toString());
        }

        // 2. 使用bool query
        List<String> list = Arrays.asList("爱" , "机器人");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for(String keyword : list){
            QueryBuilder builder = QueryBuilders.multiMatchQuery(keyword, "title", "content");
            boolQueryBuilder.should(builder);
        }
        Query query1 = new NativeSearchQueryBuilder()
                .withQuery(boolQueryBuilder)
                .withPageable(PageRequest.of(0, 10))
                .build();
        SearchHits<ArticleDoc> search1 = elasticsearchOperations.search(query, ArticleDoc.class);
        for(SearchHit<ArticleDoc> hit : search1){
            logger.info(hit.toString());
        }
    }
}
