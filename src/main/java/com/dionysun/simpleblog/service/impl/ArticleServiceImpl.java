package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.dao.ArticleRepository;
import com.dionysun.simpleblog.dao.ArticleTagRepository;
import com.dionysun.simpleblog.dao.TagRepository;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.entity.ArticleTag;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.search.ArticleDoc;
import com.dionysun.simpleblog.search.ElasticConfig;
import com.dionysun.simpleblog.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Override
    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Optional<Article> findOne(int id) {
        return articleRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteOne(int id) {
        // 更新tag
        Optional<Article> optionalArticle = articleRepository.findById(id);
        optionalArticle.ifPresent(
                article -> {
                    deleteArticleTag(article);
                    elasticsearchOperations.delete(
                            String.valueOf(id),
                            IndexCoordinates.of(ElasticConfig.INDEX_NAME)
                    );
                    articleRepository.deleteById(id);
                }
        );
    }

    @Override
    @Transactional
    public Article updateOne(Article article) {
        // 更新tag
        saveTag(article);
        // 同时更新es
        elasticsearchOperations.save(
                ArticleDoc.of(article),
                IndexCoordinates.of(ElasticConfig.INDEX_NAME)
        );
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public Article addOne(Article article) {
        article = articleRepository.save(article);
        saveTag(article);
        // 同时保存到 es
        elasticsearchOperations.save(
                ArticleDoc.of(article),
                IndexCoordinates.of(ElasticConfig.INDEX_NAME)
        );
        return article;
    }

    private void saveTag(Article article){
        if (null != article.getTagList() && null != article.getId()) {
            for (Tag tag : article.getTagList()) {
                logger.info("插入数据库" + tag.toString());
                Optional<Tag> optionalTag = tagRepository.findTagByNameEquals(tag.getName());
                if (optionalTag.isPresent()) {
                    tag = optionalTag.get();
                } else {
                    tag = tagRepository.save(tag);
                }
                logger.info("插入之后" + tag.toString());
                articleTagRepository.save(new ArticleTag(article.getId(), tag.getId()));
            }
        }
    }

    private void deleteArticleTag(Article article) {
        List<ArticleTag> deleteTagList = article.getTagList()
                .stream()
                .map(tag -> new ArticleTag(article.getId(), tag.getId()))
                .collect(Collectors.toList());
        articleTagRepository.deleteInBatch(deleteTagList);
    }
}
