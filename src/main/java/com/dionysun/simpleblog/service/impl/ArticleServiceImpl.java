package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.dao.ArticleRepository;
import com.dionysun.simpleblog.dao.TagRepository;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service("ArticleServiceImpl")
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private TagRepository tagRepository;
    @Override
    public Page<Article> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    public Optional<Article> findOne(int id) {
        return articleRepository.findById(id);
    }

    @Override
    public void deleteOne(int id) {
        articleRepository.deleteById(id);
    }

    @Override
    public Article updateOne(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article addOne(Article article) {
        if (!Objects.isNull(article.getTagList())) {
            for (Tag tag : article.getTagList()) {
                tagRepository.save(tag);
            }
        }
        return articleRepository.save(article);
    }
}
