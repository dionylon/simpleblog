package com.dionysun.simpleblog.dao;

import com.dionysun.simpleblog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    Page<Article> findAll(Specification<Article> specification, Pageable pageable);

}
