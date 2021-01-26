package com.dionysun.simpleblog.service.impl;

import com.dionysun.simpleblog.dao.ArticleRepository;
import com.dionysun.simpleblog.dao.TagRepository;
import com.dionysun.simpleblog.entity.Article;
import com.dionysun.simpleblog.entity.Tag;
import com.dionysun.simpleblog.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("TagServiceImpl")
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    private final ArticleRepository articleRepository;

    public TagServiceImpl(TagRepository tagRepository, ArticleRepository articleRepository) {
        this.tagRepository = tagRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public Page<Tag> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public void deleteOne(Tag tag) {
        tagRepository.delete(tag);
    }

    @Override
    public Page<Article> findArticlesByTag(String tagName, Pageable pageable) {
        Optional<Tag> tagOptional = tagRepository.findTagByNameEquals(tagName);
        return tagOptional
                .map(tag -> articleRepository.findByTagListContains(tag, pageable))
                .orElse(null);
    }

}
