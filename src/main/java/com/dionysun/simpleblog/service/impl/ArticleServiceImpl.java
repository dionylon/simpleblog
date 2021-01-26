package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.dao.ArticleRepository;
import com.dionysun.simpleblog.dao.ArticleTagRepository;
import com.dionysun.simpleblog.dao.TagRepository;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.entity.ArticleTag;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.service.ArticleService;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.beans.Transient;
import java.util.Objects;
import java.util.Optional;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ArticleTagRepository articleTagRepository;
    @Override
    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }
    private static final Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Override
    public Optional<Article> findOne(int id) {
        return articleRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteOne(int id) {
        // TODO 进行可能的删除
        articleRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Article updateOne(Article article) {
        // TODO 同步更新article_id表
        return articleRepository.save(article);
    }

    @Override
    @Transactional
    public Article addOne(Article article) {
        article = articleRepository.save(article);
        if (!Objects.isNull(article.getTagList())) {
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
        return article;
    }
}
