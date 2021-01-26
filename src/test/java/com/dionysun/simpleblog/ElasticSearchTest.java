package com.dionysun.simpleblog;

import com.dionysun.simpleblog.search.ArticleDoc;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;

@SpringBootTest
public class ElasticSearchTest {
    @Autowired
    ElasticsearchOperations elasticsearchOperations;
    private static final Logger logger = LoggerFactory.getLogger(ElasticSearchTest.class);
    @Test
    public void testSave(){
        ArticleDoc doc = new ArticleDoc(1, "如何赢取富婆欢心？", "我也不知道啊!!");
        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(doc.getId().toString())
                .withObject(doc)
                .build();
        String docId = elasticsearchOperations.index(indexQuery, IndexCoordinates.of("article"));
        logger.info("save done. id : " + docId);
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
}
