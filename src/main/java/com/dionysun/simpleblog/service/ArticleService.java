package com.dionysun.simpleblog.service;

import com.dionysun.simpleblog.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface ArticleService {
    /**
     * 分页查找
     */
    Page<Article> findAllArticles(Pageable pageable);

    /**
     * 根据id查找
     */
    Optional<Article> findOne(int id);

    /**
     * 删除一个
     */
    void deleteOne(int id);
    /**
     * 修改一个
     */
    Article updateOne(Article article);
    /**
     * 添加一个
     */
    Article addOne(Article article);

}
