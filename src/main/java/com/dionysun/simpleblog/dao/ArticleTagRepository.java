package com.dionysun.simpleblog.dao;

import com.dionysun.simpleblog.entity.ArticleTag;
import com.dionysun.simpleblog.entity.ArticleTagId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleTagRepository extends JpaRepository<ArticleTag, ArticleTagId> {

}
